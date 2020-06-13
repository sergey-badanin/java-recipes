package io.bsy.springjdbctemplate;

import com.zaxxer.hikari.HikariDataSource;
import io.bsy.pure.VehicleDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class VehicleConfiguration {

    @Bean
    @Qualifier("dbProperties")
    public Properties dbProperties() {
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

    @Bean
    public VehicleDao vehicleDao(@Qualifier("dbProperties") Properties props) {
        return new JdbcTemplateVehicleDao(dataSource(props));
    }

    @Bean
    public DataSource dataSource(@Qualifier("dbProperties") Properties props) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setUsername(props.getProperty("user"));
        dataSource.setPassword(props.getProperty("password"));
        dataSource.setJdbcUrl(props.getProperty("url"));
        dataSource.setMinimumIdle(2);
        dataSource.setMaximumPoolSize(5);
        return dataSource;
    }
}
