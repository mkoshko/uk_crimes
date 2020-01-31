package by.koshko.crimes.service;

import by.koshko.crimes.dao.CsvFileReader;
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
import java.util.stream.Stream;

public class ExecutionService<T, R> {

    ExecutorService requestProcessor = Executors.newFixedThreadPool(15);
    ExecutorService persistProcessor = Executors.newFixedThreadPool(15);
    private Logger logger = LoggerFactory.getLogger(ExecutionService.class);
    private RequestDataMapper<R> dataMapper;
    private HttpRequestService<R> requestService;
    private BlockingQueue<Future<String>> tasks = new LinkedBlockingQueue<>(15);
    private BlockingQueue<Future<String>> bufferedTasks = new LinkedBlockingQueue<>();
    private JsonArrayHandler<T> jsonArrayHandler;

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
        execute0(data, dateRange);
    }

    private void execute0(List<R> data, DateRange dateRange) {
        requestProcessor.execute(() -> {
            while (!(persistProcessor.isTerminated() && tasks.isEmpty())) {
                try {
                    if (tasks.isEmpty()) {
                        TimeUnit.MILLISECONDS.sleep(100);
                    }
                    Future<String> task = tasks.take();
                    while (!task.isDone()) {
                        TimeUnit.MILLISECONDS.sleep(100);
                    }
                    persistProcessor.execute(() -> {
                        try {
                            logger.info("Processing response...");
                            jsonArrayHandler.process(task.get());
                        } catch (ServiceException e) {
                            logger.error("Cannot process the response. {}", e.getMessage());
                        } catch (InterruptedException | ExecutionException e1) {
                            logger.error(e1.getMessage());
                        }
                    });
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        dateRange.forEach(date -> data.forEach(point -> {
            Future<String> task = processRequest(point, date);
            try {
                tasks.put(task);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }));
    }

    private void throttle() {

    }

    private Future<String> processRequest(R point, String date) {
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
