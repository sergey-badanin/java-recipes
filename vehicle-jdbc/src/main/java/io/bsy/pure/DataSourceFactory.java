package io.bsy.pure;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataSourceFactory {
    public static DataSource getDataSource() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        var props = getDbProperties();
        ds.setDatabaseName(props.getProperty("databaseName"));
        ds.setServerNames(new String[]{props.getProperty("serverName")});
        ds.setUser(props.getProperty("user"));
        ds.setPassword(props.getProperty("password"));
        return ds;
    }

    public static Properties getDbProperties() {
        try (InputStream propertiesStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("db.properties")
        ) {
            Properties props = new Properties();
            props.load(propertiesStream);
            return props;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
