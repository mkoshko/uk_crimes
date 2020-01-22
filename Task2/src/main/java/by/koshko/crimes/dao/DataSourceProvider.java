package by.koshko.crimes.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.util.Properties;

public class DataSourceProvider {

    private static final String PROPERTIES_FILE = "dataSource.properties";
    private HikariDataSource dataSource;

    public DataSourceProvider() throws DaoException {
        initDataSource();
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    private void initDataSource() throws DaoException {
        try {
            Properties dataSourceProperties = new PropertiesReader().loadProperties(PROPERTIES_FILE);
            dataSource = new HikariDataSource(new HikariConfig(dataSourceProperties));
        } catch (IOException e) {
            throw new DaoException("Cannot initialize DataSource." , e);
        }
    }

}
