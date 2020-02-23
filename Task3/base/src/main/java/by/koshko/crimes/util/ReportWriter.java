package by.koshko.crimes.util;

import by.koshko.crimes.service.exception.ApplicationException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportWriter {

    public static void writeReport(List<?> data, String name, String header) throws ApplicationException {
        String fileName = String.format("%s %s.txt", name,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss")));
        try (FileWriter fileWriter = new FileWriter(new File(fileName), false)) {
            fileWriter.append(header).append("\n");
            for (Object o : data)
                fileWriter.append(o.toString()).append("\n");
            fileWriter.flush();
        } catch (IOException e) {
            throw new ApplicationException("Cannot write report to the file.", e);
        }
    }

}
