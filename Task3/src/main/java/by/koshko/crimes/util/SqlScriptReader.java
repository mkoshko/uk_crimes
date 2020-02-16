package by.koshko.crimes.util;

import by.koshko.crimes.service.exception.ApplicationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SqlScriptReader {

    public static String readSql(String file) throws ApplicationException {
        try {
            return new String(Files.readAllBytes(Path.of(file)));
        } catch (IOException e) {
            throw new ApplicationException("Cannot read sql file.", e);
        }
    }

}
