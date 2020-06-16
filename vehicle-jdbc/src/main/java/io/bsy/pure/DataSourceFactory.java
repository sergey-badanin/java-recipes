package io.bsy.pure;

import io.bsy.utils.PropertiesProvider;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class DataSourceFactory {
    public static DataSource getDataSource() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        var props = PropertiesProvider.getDbProperties();
        ds.setDatabaseName(props.getProperty("databaseName"));
        ds.setServerNames(new String[]{props.getProperty("serverName")});
        ds.setUser(props.getProperty("user"));
        ds.setPassword(props.getProperty("password"));
        return ds;
    }
}
