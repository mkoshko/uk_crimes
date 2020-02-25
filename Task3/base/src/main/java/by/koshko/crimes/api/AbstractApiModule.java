package by.koshko.crimes.api;

import by.koshko.crimes.service.*;
import by.koshko.crimes.service.impl.JsonArrayHandlerImpl;
import by.koshko.crimes.service.request.RequestService;
import by.koshko.crimes.util.CommandLineParameters;
import by.koshko.crimes.util.CsvFileReader;
import by.koshko.crimes.util.DateRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

public abstract class AbstractApiModule<T, U> implements ApplicationApiModule {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private RequestDataMapper<U> requestDataMapper;
    private HttpRequestUrlBuilder<U> httpRequestUrlBuilder;
    private JsonToModelMapper<T> jsonToObjectMapper;
    private PersistenceService<T> persistenceService;

    public AbstractApiModule(JsonToModelMapper<T> jsonToObjectMapper,
                             PersistenceService<T> persistenceService,
                             RequestDataMapper<U> requestDataMapper,
                             HttpRequestUrlBuilder<U> httpRequestUrlBuilder) {
        this.requestDataMapper = requestDataMapper;
        this.jsonToObjectMapper = jsonToObjectMapper;
        this.persistenceService = persistenceService;
        this.httpRequestUrlBuilder = httpRequestUrlBuilder;
    }

    @Override
    public void run(Properties parameters) {
        ExecutionService<U> executionService = new ExecutionService<>(
                new JsonArrayHandlerImpl<>(jsonToObjectMapper, persistenceService),
                new RequestService<U>(httpRequestUrlBuilder)
        );
        DateRange dateRange = DateRange.build(
                parameters.getProperty(CommandLineParameters.FROM_OPTION),
                parameters.getProperty(CommandLineParameters.TO_OPTION));
        List<U> requestParams = readAndMapDataFromFile(parameters.getProperty(CommandLineParameters.FILE_OPTION));
        executionService.execute(requestParams, dateRange);
    }

    private List<U> readAndMapDataFromFile(String file) {
        Stream<String> data = CsvFileReader.getLinesAsStream(file);
        return requestDataMapper.map(data);
    }
}
