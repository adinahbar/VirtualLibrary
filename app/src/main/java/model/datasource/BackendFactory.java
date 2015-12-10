package model.datasource;

import model.backend.Backend;

/**
 * Created by adina_000 on 19-Nov-15.
 */
public class BackendFactory {
    static Backend instance = null;

    public final static Backend getInstance()
    {
        if (instance == null)
              instance = new model.datasource.DatabaseList();
        return instance;

    }
}
