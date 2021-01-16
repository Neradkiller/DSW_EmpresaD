package mercadeoucab.servicio;


import mercadeoucab.utilidades.JWT;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

@ApplicationPath( "/api/v1" )
public class AplicacionBase extends Application {

    void validateCredentials(String credential, String userId )
    {
        try
        {
            JWT.verifyToken( credential, userId );
        }
        catch ( Exception e )
        {
            throwException( Response.Status.UNAUTHORIZED, e );
        }
    }

    void verifyParams( Object object )
    {
        if ( object == null )
            throwException( Response.Status.BAD_REQUEST );
    }

    void throwException( Response.Status status, Exception e )
    {
        throw new WebApplicationException(Response.status( status ).entity( e ).build() );
    }

    void throwException( Response.Status status )
    {
        throw new WebApplicationException( Response.status( status ).build() );
    }
}
