package by.koshko.crimes.util;

import by.koshko.crimes.service.exception.ApplicationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public final class CsvFileReader {

    private CsvFileReader() {
    }

    public static Stream<String> getLinesAsStream(String filePath) {
        try {
            return Files.lines(Paths.get(filePath));
        } catch (IOException e) {
            throw new ApplicationException("Cannot read file.", e);
        }
    }
}
