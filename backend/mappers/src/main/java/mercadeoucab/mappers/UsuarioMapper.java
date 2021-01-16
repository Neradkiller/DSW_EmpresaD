package mercadeoucab.mappers;

import mercadeoucab.dtos.DtoUsuario;
import mercadeoucab.entidades.Usuario;

public class UsuarioMapper
{

    public static Usuario mapDtoToEntity(DtoUsuario dto )
    {
        Usuario entity = new Usuario(dto.get_id());

        entity.setNombre( dto.getNombre() );
        entity.setApellido( dto.getApellido() );
        entity.setRol( dto.getRol() );
        entity.setCorreo( dto.getCorreo() );
        entity.setEstado( dto.getEstado() );
        entity.setPassword(dto.getPassword() );

        return entity;
    }

    public static DtoUsuario mapEntityToDto( Usuario entity )
    {
        final DtoUsuario dto = new DtoUsuario();

        dto.set_id(entity.get_id());
        dto.setNombre( entity.getNombre());
        dto.setApellido( entity.getApellido());
        dto.setCorreo(entity.getCorreo());
        dto.setEstado( entity.getEstado() );
        dto.setRol( entity.getRol() );
        dto.setPassword(entity.getPassword() );
        dto.setActivo( entity.getActivo() );
        dto.setCreado_el( entity.getCreado_el() );
        dto.setModificado_el( entity.getModificado_el() );
        dto.setToken( entity.getToken() );
        return dto;
    }
}