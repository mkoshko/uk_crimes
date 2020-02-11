package by.koshko.crimes.api;

import by.koshko.crimes.entity.Crime;
import by.koshko.crimes.entity.Point;
import by.koshko.crimes.exec.ApplicationAPIModule;
import by.koshko.crimes.service.ExecutionService;
import by.koshko.crimes.service.PersistenceService;
import by.koshko.crimes.service.exception.ApplicationException;
import by.koshko.crimes.service.impl.PointHttpRequestService;
import by.koshko.crimes.service.mapper.CrimeMapper;
import by.koshko.crimes.service.mapper.PointMapper;
import by.koshko.crimes.util.CommandLineParameters;
import by.koshko.crimes.util.CsvFileReader;
import by.koshko.crimes.util.DateRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

@Component("street-level-crimes")
public class StreetLevelCrimesAPIModule implements ApplicationAPIModule {

    private static final String REQUEST_URL = "https://data.police.uk/api/crimes-street/all-crime";
    private static Logger logger = LoggerFactory.getLogger(StreetLevelCrimesAPIModule.class);
    private PointMapper pointMapper;
    private PointHttpRequestService requestService;
    private CrimeMapper crimeMapper;
    private PersistenceService<Crime> persistenceService;

    @Autowired
    public StreetLevelCrimesAPIModule(PointMapper pointMapper,
                                      CrimeMapper crimeMapper,
                                      PersistenceService<Crime> persistenceService) {
        this.pointMapper = pointMapper;
        this.requestService = new PointHttpRequestService(REQUEST_URL);
        this.crimeMapper = crimeMapper;
        this.persistenceService = persistenceService;
    }

    @Override
    public void run(Properties parameters) {
        try {
            ExecutionService<Crime, Point> executionService
                    = new ExecutionService<>(requestService, crimeMapper, persistenceService);
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
