package mercadeoucab.servicio;

import mercadeoucab.accesodatos.DaoUsuario;
import mercadeoucab.directorioactivo.DirectorioActivo;
import mercadeoucab.dtos.DtoDirectorioAUser;
import mercadeoucab.entidades.Usuario;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path( "/LDAP" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class ServicioLDAP extends AplicacionBase {

    @POST
    @Path("/login")
    public Response login(DtoDirectorioAUser dtoUsuario){
        JsonObject data;
        JsonObject usuarioRegresado;
        Response resultado = null;
        try{
            DaoUsuario dao = new DaoUsuario();
            Usuario usuario = dao.obtenerUsuarioPorCorreo(
                    dtoUsuario.getCorreo()
            );
            DirectorioActivo ldap = new DirectorioActivo(
                    usuario.getRol()
            );
            dtoUsuario.setEstado(
                    usuario.getEstado()
            );

            if ( ldap.userAuthentication( dtoUsuario)){
                usuarioRegresado = Json.createObjectBuilder()
                        .add("_id", usuario.get_id())
                        .add( "nombre", usuario.getNombre())
                        .add( "apellido", usuario.getApellido())
                        .add( "rol", usuario.getRol())
                        .add( "estado", usuario.getEstado())
                        .add( "correo", usuario.getCorreo())
                        .build();
                data = Json.createObjectBuilder()
                        .add("status", 200)
                        .add("data", usuarioRegresado)
                        .build();
                resultado = Response.status(Response.Status.OK)
                                    .entity(data)
                                    .build();
            }

        }catch (Exception e){
            e.printStackTrace();
            data = Json.createObjectBuilder()
                        .add("status", 400)
                        .add("error", "No se pudo iniciar sesion")
                        .build();
            resultado = Response.status(Response.Status.BAD_REQUEST)
                                .entity(data)
                                .build();
        }
        return resultado;
    }
}
