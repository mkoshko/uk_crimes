package by.koshko.crimes.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    public Properties loadProperties(String filename) throws IOException {
        return readPropertiesFromInputStream(filename);
    }

    private Properties readPropertiesFromInputStream(String filename) throws IOException {
            InputStream inputStream = getResourceAsStream(filename);
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;

    }

    private InputStream getResourceAsStream(String filename) {
        return PropertiesReader.class.getClassLoader().getResourceAsStream(filename);
    }

}
