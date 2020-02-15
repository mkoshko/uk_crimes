package by.koshko.crimes.util;

import by.koshko.crimes.service.exception.ApplicationException;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportWriter {

    public static void writeReport(List<?> data) throws ApplicationException {
        String fileName = String.format("%s-%s.txt", LocalDate.now(), System.currentTimeMillis());
        try {
            FileWriter fileWriter = new FileWriter(new File(fileName),true);
            data.forEach(o -> {
                try {
                    fileWriter.append(o.toString() + "\n");
                } catch (IOException e) {
                    LoggerFactory.getLogger(ReportWriter.class).error(e.getMessage());
                }
            });
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApplicationException("Cannot write to the file.", e);
        }
    }

}
