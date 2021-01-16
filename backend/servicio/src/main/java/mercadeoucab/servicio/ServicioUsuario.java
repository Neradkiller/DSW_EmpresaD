package mercadeoucab.servicio;

import mercadeoucab.accesodatos.DaoUsuario;
import mercadeoucab.dtos.DtoUsuario;
import mercadeoucab.entidades.Usuario;
import mercadeoucab.mappers.UsuarioMapper;
import mercadeoucab.utilidades.JWT;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Path( "/usuarios" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class ServicioUsuario extends AplicacionBase{


    @POST
    @Path("/registrar")
    public Response registrarUsuario(DtoUsuario dtoUsuario){
        JsonObject data;
        Response resultado = null;
        try {

            verifyParams(dtoUsuario);
            DaoUsuario dao = new DaoUsuario();
            Usuario usuario = UsuarioMapper.mapDtoToEntity(dtoUsuario);
            usuario.setActivo(1);
            usuario.setCreado_el(new Date(Calendar
                                        .getInstance()
                                        .getTime()
                                        .getTime()));
            Usuario resul = dao.insert(usuario);
            String token = JWT.createToken( String.valueOf( resul.get_id()));
            resul.setToken(token);
            Usuario usuario1 = dao.update(resul);
            DtoUsuario objeto = UsuarioMapper.mapEntityToDto(usuario1);
            JsonObject objetoSerializado = Json.createObjectBuilder()
                                                .add("_id", objeto.get_id())
                                                .add("nombre", objeto.getNombre())
                                                .add("apellido", objeto.getApellido())
                                                .add("rol", objeto.getRol())
                                                .add("estado", objeto.getEstado())
                                                .add("correo", objeto.getCorreo())
                                                .add("token", objeto.getToken())
                                                .build();
            data = Json.createObjectBuilder()
                    .add("status", 200)
                    .add("message", "Usuario registrado exitosamente")
                    .add("data",objetoSerializado)
                    .build();
            resultado = Response.status(Response.Status.OK)
                    .entity(data)
                    .build();
        }
        catch (Exception e){
            String problema = e.getMessage();
            data = Json.createObjectBuilder()
                    .add("status", 400)
                    .add("message", problema)
                    .build();
            resultado = Response.status(Response.Status.BAD_REQUEST)
                    .entity(data)
                    .build();
        }
        return resultado;
    }

    @POST
    @Path("/consultar")
    public Response consultarUsuario(DtoUsuario dtoUsuario){
        JsonObject data;
        Response resultado = null;
        try {
            verifyParams( dtoUsuario );
            //validateCredentials(dtoUsuario.getToken(), String.valueOf(dtoUsuario.get_id()));
        }
        catch (Exception e){
            String problema = e.getMessage();
            data = Json.createObjectBuilder()
                    .add("status", 400)
                    .add("message", problema)
                    .build();
            resultado = Response.status(Response.Status.BAD_REQUEST)
                    .entity(data)
                    .build();
        }
        return resultado;
    }

    @POST
    @Path( "/generateToken" )
    public String generateToken(DtoUsuario dtoUsuario)
    {
        DtoUsuario resultado = new DtoUsuario();
        String token = "";
        try
        {
            verifyParams( dtoUsuario );
            token = JWT.createToken( String.valueOf( dtoUsuario.get_id() ));

        }
        catch ( Exception ex )
        {
            String problema = ex.getMessage();
        }
        return  token;
    }

    @POST
    @Path( "/validateToken" )
    public String validateToken(DtoUsuario dtoUsuario)
    {
        DtoUsuario resultado = new DtoUsuario();
        String idUsuario = "";
        try
        {
            verifyParams( dtoUsuario );
            idUsuario = JWT.verifyToken( dtoUsuario.getToken() );

        }
        catch ( Exception ex )
        {
            String problema = ex.getMessage();
        }
        return  idUsuario;
    }
}
