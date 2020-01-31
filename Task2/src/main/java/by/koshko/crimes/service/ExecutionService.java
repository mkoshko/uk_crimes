package by.koshko.crimes.service;

import by.koshko.crimes.dao.CsvFileReader;
import by.koshko.crimes.entity.FailedRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class ExecutionService<T, R> {

    ExecutorService executorService = Executors.newFixedThreadPool(30);
    ExecutorService requestExecutor = Executors.newFixedThreadPool(30);
    private Logger logger = LoggerFactory.getLogger(ExecutionService.class);
    private RequestDataMapper<R> dataMapper;
    private HttpRequestService<R> requestService;
    private JsonArrayHandler<T> jsonArrayHandler;
    private List<FailedRequest<R>> failedRequests = new ArrayList<>();
    private AtomicInteger numberOfRequests = new AtomicInteger(0);
    private final int API_CALL_LIMIT = 15;
    private long startTime = System.currentTimeMillis();

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
        data.forEach(point -> dateRange.stream().forEach(date -> {
            checkTimeAndReset();
            processRequest(point, date);
        }));
    }

    private void processRequest(R point ,String date) {
        executorService.execute(() -> {
            try {
                while (isApiCallLimitExceeded()) {
                    TimeUnit.MILLISECONDS.sleep(50);
                }
                numberOfRequests.incrementAndGet();
                Future<String> response = requestExecutor.submit(() -> requestService.sendRequest(point, date));
                while (!response.isDone()) {
                    TimeUnit.MILLISECONDS.sleep(100);
                }
                numberOfRequests.decrementAndGet();
                jsonArrayHandler.process(response.get());
            } catch (ServiceException e) {
                failedRequests.add(new FailedRequest<>(point, date));
                logger.error("Cannot process the request. {}", e.getMessage());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                logger.error(e.getMessage());
            }
        });
    }

    private void checkTimeAndReset() {
        if (System.currentTimeMillis() - startTime > TimeUnit.SECONDS.toMillis(1)) {
            numberOfRequests.set(0);
            startTime = System.currentTimeMillis();
        }
    }

    private boolean isApiCallLimitExceeded() {
        return numberOfRequests.get() > API_CALL_LIMIT;
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
