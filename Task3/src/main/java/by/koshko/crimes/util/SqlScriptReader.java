package by.koshko.crimes.util;

import by.koshko.crimes.service.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SqlScriptReader {

    private static final Logger logger = LoggerFactory.getLogger(SqlScriptReader.class);

    public static String readSql(String file) throws ApplicationException {
        try {
            return new String(Files.readAllBytes(Path.of(file)));
        } catch (IOException e) {
            throw new ApplicationException("Cannot read sql file.", e);
        }
    }

}
