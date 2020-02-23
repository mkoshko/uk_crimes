package by.koshko.crimes.service;

import by.koshko.crimes.service.exception.ServiceException;
import by.koshko.crimes.service.jsonutil.JsonArrayHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutionService<T, U> {

    private Logger logger = LoggerFactory.getLogger(ExecutionService.class);

    private static final int TASKS_LIMIT = 15;

    private ExecutorService requestProcessor = Executors.newFixedThreadPool(10);
    private ExecutorService persistProcessor = Executors.newFixedThreadPool(10);
    private BlockingQueue<Future<String>> tasks = new LinkedBlockingQueue<>(16);
    private AtomicInteger processedResponses = new AtomicInteger();
    private HttpRequestService requestService = new HttpRequestService();
    private HttpRequestUrlBuilder<U> requestUrlBuilder;
    private JsonArrayHandler<T> jsonArrayHandler;
    private int totalRequests = 0;

    public ExecutionService(JsonToObjectMapper<T> jsonToObjectMapper,
                            PersistenceService<T> persistenceService,
                            HttpRequestUrlBuilder<U> httpRequestUrlBuilder) {
        jsonArrayHandler = new JsonArrayHandler<>(jsonToObjectMapper, persistenceService);
        requestUrlBuilder = httpRequestUrlBuilder;
    }

    public void execute(Iterable<U> requestParams, Iterable<String> range) {
        persistProcessor.execute(() -> {
            processTasksQueue();
            persistProcessor.shutdown();
        });
        range.forEach(rangeValue -> requestParams.forEach(requestParam -> {
            Future<String> task = sendRequest(requestParam, rangeValue);
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

    private Future<String> sendRequest(U u, String date) {
        return requestProcessor.submit(() -> {
            try {
                return requestService.sendRequest(requestUrlBuilder.buildRequestUrl(u, date));
            } catch (ServiceException e) {
                logger.info(e.getMessage());
            }
            return null;
        });
    }
}
