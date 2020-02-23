package by.koshko.crimes.api;

import by.koshko.crimes.model.Point;
import by.koshko.crimes.service.ExecutionService;
import by.koshko.crimes.service.JsonToObjectMapper;
import by.koshko.crimes.service.PersistenceService;
import by.koshko.crimes.service.exception.ApplicationException;
import by.koshko.crimes.service.request.PointHttpRequestService;
import by.koshko.crimes.service.mapper.PointMapper;
import by.koshko.crimes.util.CommandLineParameters;
import by.koshko.crimes.util.CsvFileReader;
import by.koshko.crimes.util.DateRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

public abstract class AbstractPointBasedApiModule<T> implements ApplicationApiModule {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private PointMapper pointMapper;
    private PointHttpRequestService requestService;
    private JsonToObjectMapper<T> jsonToObjectMapper;
    private PersistenceService<T> persistenceService;

    public AbstractPointBasedApiModule(PointMapper pointMapper,
                                       JsonToObjectMapper<T> jsonToObjectMapper,
                                       PersistenceService<T> persistenceService,
                                       PointHttpRequestService requestService) {
        this.pointMapper = pointMapper;
        this.requestService = requestService;
        this.jsonToObjectMapper = jsonToObjectMapper;
        this.persistenceService = persistenceService;
    }

    @Override
    public void run(Properties parameters) {
        try {
            ExecutionService<T, Point> executionService
                    = new ExecutionService<>(requestService, jsonToObjectMapper, persistenceService);
            DateRange dateRange = DateRange.build(
                    parameters.getProperty(CommandLineParameters.FROM_OPTION),
                    parameters.getProperty(CommandLineParameters.TO_OPTION));
            List<Point> requestParams = readAndMapDataFromFile(parameters.getProperty(CommandLineParameters.FILE_OPTION));
            executionService.execute(requestParams, dateRange);
        } catch (ApplicationException e) {
            logger.error(e.getMessage());
        }
    }

    private List<Point> readAndMapDataFromFile(String file) throws ApplicationException {
        try {
            Stream<String> data = CsvFileReader.getLinesAsStream(file);
            return pointMapper.map(data);
        } catch (IOException e) {
            throw new ApplicationException("Cannot read data from file.", e);
        }
    }
}
