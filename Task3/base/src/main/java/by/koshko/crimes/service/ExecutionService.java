package by.koshko.crimes.service;

import by.koshko.crimes.service.exception.ServiceException;
import by.koshko.crimes.service.request.RequestService;
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

public class ExecutionService<T> {

    private Logger logger = LoggerFactory.getLogger(ExecutionService.class);

    private static final int REQUEST_THREADS = 10;
    private static final int PERSIST_THREADS = 10;
    private static final int REQUEST_LIMIT = 15;

    private ExecutorService requestExecutor = Executors.newFixedThreadPool(REQUEST_THREADS);
    private ExecutorService persistExecutor = Executors.newFixedThreadPool(PERSIST_THREADS);
    private BlockingQueue<Future<String>> tasks = new LinkedBlockingQueue<>();
    private RequestService<T> requestService;
    private JsonArrayHandler jsonArrayHandler;
    private AtomicInteger processedResponses = new AtomicInteger();
    private int totalRequests = 0;

    public ExecutionService(JsonArrayHandler jsonArrayHandler, RequestService<T> requestService) {
        this.jsonArrayHandler = jsonArrayHandler;
        this.requestService = requestService;
    }

    public void execute(Iterable<T> requestParams, Iterable<String> range) {
        persistExecutor.execute(() -> {
            processTasksQueue();
            persistExecutor.shutdown();
        });
        range.forEach(rangeValue -> requestParams.forEach(requestParam -> {
            logger.info("Sending request for {}.", requestParam);
            Future<String> task = sendRequest(requestParam, rangeValue);
            ++totalRequests;
            putTaskInQueue(task);
        }));
        requestExecutor.shutdown();
        waitForTermination();
        report();
    }

    private void waitForTermination() {
        while (!persistExecutor.isTerminated()) {
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
        jsonArrayHandler.process(jsonResponse);
        processedResponses.incrementAndGet();
    }

    private void processTasksQueue() {
        while (!(requestExecutor.isShutdown() && tasks.isEmpty())) {
            try {
                Future<String> task = tasks.take();
                String response = returnValueIfComplete(task);
                if (response != null) {
                    persistExecutor.execute(() -> processResponse(response));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void putTaskInQueue(Future<String> task) {
        try {
            while (tasks.size() >= REQUEST_LIMIT) {
                sleepMillis(1);
            }
            tasks.put(task);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private Future<String> sendRequest(T model, String date) {
        return requestExecutor.submit(() -> requestService.sendRequest(model, date));
    }

    private void sleepMillis(long milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void report() {
        logger.info("Total requests: [{}]. Total success responses [{}]. Failed [{}].",
                totalRequests, processedResponses.get(), totalRequests - processedResponses.get());
    }
}
