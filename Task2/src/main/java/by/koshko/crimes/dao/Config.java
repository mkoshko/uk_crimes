package by.koshko.crimes.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "by.koshko.crimes")
public class Config {

    private static final String PROPERTIES_FILE = "dataSource.properties";

    @Bean
    public DataSource getDataSource() throws DaoException {
        try {
            Properties dataSourceProperties = new PropertiesReader().loadProperties(PROPERTIES_FILE);
            return new HikariDataSource(new HikariConfig(dataSourceProperties));
        } catch (IOException e) {
            throw new DaoException("Cannot initialize DataSource.", e);
        }
    }

    @Bean
    public FluentJdbc getFluentJdbc(DataSource dataSource) {
        return new FluentJdbcBuilder()
                .connectionProvider(dataSource)
                .build();
    }
}
