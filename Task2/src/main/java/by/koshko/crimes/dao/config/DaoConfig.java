package by.koshko.crimes.dao.config;

import by.koshko.crimes.dao.exception.DaoException;
import by.koshko.crimes.util.PropertiesReader;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class DaoConfig {

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
                .connectionProvider(new TransactionAwareDataSourceProxy(dataSource))
                .build();
    }

    @Bean
    public DataSourceTransactionManager getTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
