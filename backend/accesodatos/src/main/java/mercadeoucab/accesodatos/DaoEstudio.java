package mercadeoucab.accesodatos;

import mercadeoucab.entidades.Estudio;

import javax.persistence.EntityManager;

public class DaoEstudio extends Dao<Estudio>{

    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();

    public DaoEstudio( )
    {
        super( _handler );
    }
}
