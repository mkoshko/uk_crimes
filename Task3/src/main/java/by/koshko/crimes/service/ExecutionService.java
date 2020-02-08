package by.koshko.crimes.service;

import by.koshko.crimes.service.exception.ApplicationException;
import by.koshko.crimes.service.exception.ServiceException;
import by.koshko.crimes.service.jsonutil.JsonArrayHandler;
import by.koshko.crimes.util.CsvFileReader;
import by.koshko.crimes.util.DateRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class ExecutionService<T, R> {

    private static final int TASKS_LIMIT = 15;
    private Logger logger = LoggerFactory.getLogger(ExecutionService.class);

    private ExecutorService requestProcessor = Executors.newFixedThreadPool(5);
    private ExecutorService persistProcessor = Executors.newFixedThreadPool(20);
    private BlockingQueue<Future<String>> tasks = new LinkedBlockingQueue<>(16);
    private AtomicInteger processedResponses = new AtomicInteger();
    private RequestDataMapper<R> dataMapper;
    private HttpRequestService<R> requestService;
    private JsonArrayHandler<T> jsonArrayHandler;
    private int totalRequests = 0;

    public ExecutionService(RequestDataMapper<R> dataMapper,
                            HttpRequestService<R> requestService,
                            JsonToObjectMapper<T> objectMapper,
                            PersistenceService<T> persistenceService) {
        this.dataMapper = dataMapper;
        this.requestService = requestService;
        jsonArrayHandler = new JsonArrayHandler<>(objectMapper, persistenceService);
    }

    public void execute(String file, String startDate, String endDate) throws ApplicationException {
        DateRange dateRange = DateRange.build(startDate, endDate);
        List<R> data = readAndMapDataFromFile(file);
        processDataInDateRange(data, dateRange);
    }

    private void processDataInDateRange(List<R> coordinates, DateRange dateRange) {
        persistProcessor.execute(() -> {
            processTasksQueue();
            persistProcessor.shutdown();
        });
        dateRange.forEach(date -> coordinates.forEach(point -> {
            Future<String> task = sendRequest(point, date);
            ++totalRequests;
            putTaskInQueue(task);
        }));
        requestProcessor.shutdown();
        waitForTermination();
        report();
    }

    private void waitForTermination() {
        while (!persistProcessor.isTerminated()) {
            sleepMillis(500);
        }
    }

    private String returnValueIfComplete(Future<String> task) {
        try {
            if (task.isDone()) {
                return task.get();
            } else {
                tasks.put(task);
            }
        } catch (ExecutionException e) {
            logger.error(e.getMessage());
        } catch (InterruptedException e1) {
            Thread.currentThread().interrupt();
        }
        return null;
    }

    private void processResponse(String jsonResponse) {
        try {
            jsonArrayHandler.process(jsonResponse);
            processedResponses.incrementAndGet();
        } catch (ServiceException e) {
            logger.error("Cannot process the response. {}", e.getMessage());
        }
    }

    private void processTasksQueue() {
        while (!(requestProcessor.isShutdown() && tasks.isEmpty())) {
            try {
                Future<String> task = tasks.take();
                String response = returnValueIfComplete(task);
                if (response != null) {
                    persistProcessor.execute(() -> processResponse(response));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void putTaskInQueue(Future<String> task) {
        try {
            while (tasks.size() >= TASKS_LIMIT) {
                sleepMillis(1);
            }
            tasks.put(task);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void sleepMillis(long milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void report() {
        System.out.println(String.format("Total requests: [%d]. Total success responses [%d]. Failed [%d].",
                totalRequests, processedResponses.get(), totalRequests - processedResponses.get()));
    }

    private Future<String> sendRequest(R point, String date) {
        return requestProcessor.submit(() -> requestService.sendRequest(point, date));
    }

    private List<R> readAndMapDataFromFile(String file) throws ApplicationException {
        try {
            Stream<String> data = CsvFileReader.getLinesAsStream(file);
            return dataMapper.map(data);
        } catch (IOException e) {
            throw new ApplicationException("Cannot read data from file.", e);
        }
    }
}
