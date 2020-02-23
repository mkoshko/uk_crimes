package by.koshko.crimes.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public final class CsvFileReader {

    private CsvFileReader() {
    }

    public static Stream<String> getLinesAsStream(String filePath) throws IOException {
        return Files.lines(Paths.get(filePath));
    }
}
