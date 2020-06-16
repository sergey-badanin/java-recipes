package io.bsy.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesProvider {

    public static Properties getDbProperties() {
        return getPropertiesFromFile("db.properties");
    }

    public static Properties getHiberProperties() {
        return getPropertiesFromFile("hibernate.properties");
    }

    private static Properties getPropertiesFromFile(String name) {
        try (InputStream propertiesStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(name)
        ) {
            Properties props = new Properties();
            props.load(propertiesStream);
            return props;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
