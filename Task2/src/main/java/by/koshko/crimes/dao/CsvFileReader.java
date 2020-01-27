package by.koshko.crimes.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class CsvFileReader {

    private String filePath;

    public CsvFileReader(String filePath) {
        this.filePath = filePath;
    }

    public Stream<String> getLinesAsStream() throws IOException {
        return Files.lines(Paths.get(filePath));
    }
}
