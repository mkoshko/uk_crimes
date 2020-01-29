package by.koshko.crimes.service;

import by.koshko.crimes.dao.CsvFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ExecutorService<T, R> {

    private Logger logger = LoggerFactory.getLogger(ExecutorService.class);
    private RequestDataMapper<R> dataMapper;
    private HttpRequestService<R> requestService;
    private JsonArrayHandler<T> jsonArrayHandler;

    public ExecutorService(RequestDataMapper<R> dataMapper,
                           HttpRequestService<R> requestService,
                           JsonToObjectMapper<T> objectMapper,
                           PersistenceService<T> persistenceService) {
        this.dataMapper = dataMapper;
        this.requestService = requestService;
        jsonArrayHandler = new JsonArrayHandler<>(objectMapper, persistenceService);
    }

    public void execute(String file, String startDate, String endDate) throws ExecutionException {
        DateRange dateRange = createDateRange(startDate, endDate);
        List<R> data = readDataFromFile(file);
        execute0(data, dateRange);
    }

    private void execute0(List<R> data, DateRange dateRange) {
        data.parallelStream().forEach(point -> dateRange.stream().forEach(date -> {
            try {
                String jsonResponse = requestService.sendRequest(point, date);
                jsonArrayHandler.process(jsonResponse);
            } catch (ServiceException e) {
                logger.error(e.getMessage());
            }
        }));
    }

    private DateRange createDateRange(String startDate, String endDate) throws ExecutionException {
        try {
            if (Objects.nonNull(startDate) || Objects.nonNull(endDate)) {
                return new DateRange(startDate, endDate);
            }
        } catch (DateTimeParseException e) {
            throw new ExecutionException("Invalid date format.");
        }
        throw new ExecutionException("Invalid date range.");
    }

    private List<R> readDataFromFile(String file) throws ExecutionException {
        try {
            Stream<String> data = CsvFileReader.getLinesAsStream(file);
            return dataMapper.map(data);
        } catch (IOException e) {
            throw new ExecutionException("Cannot read data from file.", e);
        }
    }
}