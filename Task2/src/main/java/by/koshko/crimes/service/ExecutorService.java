package by.koshko.crimes.service;

import by.koshko.crimes.dao.CsvFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
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

    public void execute(String file, String dateFrom, String dateTo) {
        try {
            List<R> data = readDataFromFile(file);
            data.parallelStream().forEach(point -> {
                try {
                    String jsonResponse = requestService.sendRequest(point);
                    jsonArrayHandler.process(jsonResponse);
                } catch (ServiceException e) {
                    logger.error(e.getMessage());
                }
            });
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private List<R> readDataFromFile(String file) throws IOException {
        Stream<String> data = CsvFileReader.getLinesAsStream(file);
        return dataMapper.map(data);
    }
}
