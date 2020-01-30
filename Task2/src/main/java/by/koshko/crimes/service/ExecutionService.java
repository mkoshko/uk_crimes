package by.koshko.crimes.service;

import by.koshko.crimes.dao.CsvFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class ExecutionService<T, R> {

    ExecutorService executorService = Executors.newFixedThreadPool(15);
    private Logger logger = LoggerFactory.getLogger(ExecutionService.class);
    private RequestDataMapper<R> dataMapper;
    private HttpRequestService<R> requestService;
    private JsonArrayHandler<T> jsonArrayHandler;
    private ConcurrentLinkedQueue<Future<String>> futures = new ConcurrentLinkedQueue<>();

    public ExecutionService(RequestDataMapper<R> dataMapper,
                            HttpRequestService<R> requestService,
                            JsonToObjectMapper<T> objectMapper,
                            PersistenceService<T> persistenceService) {
        this.dataMapper = dataMapper;
        this.requestService = requestService;
        jsonArrayHandler = new JsonArrayHandler<>(objectMapper, persistenceService);
    }

    public void execute(String file, String startDate, String endDate) throws ApplicationException {
        DateRange dateRange = createDateRange(startDate, endDate);
        List<R> data = readDataFromFile(file);
        execute0(data, dateRange);
    }

    private void execute0(List<R> data, DateRange dateRange) {
        long start = System.currentTimeMillis();
        data.parallelStream().forEach(point -> dateRange.forEach(date -> {
            Future<String> response = executorService.submit(() -> requestService.sendRequest(point, date));
            try {
                while (!response.isDone()) {
                    TimeUnit.MILLISECONDS.sleep(500);
                }
                jsonArrayHandler.process(response.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    private DateRange createDateRange(String startDate, String endDate) throws ApplicationException {
        try {
            if (Objects.nonNull(startDate) || Objects.nonNull(endDate)) {
                return new DateRange(startDate, endDate);
            }
        } catch (DateTimeParseException e) {
            throw new ApplicationException("Invalid date format.");
        }
        throw new ApplicationException("Invalid date range.");
    }

    private List<R> readDataFromFile(String file) throws ApplicationException {
        try {
            Stream<String> data = CsvFileReader.getLinesAsStream(file);
            return dataMapper.map(data);
        } catch (IOException e) {
            throw new ApplicationException("Cannot read data from file.", e);
        }
    }
}