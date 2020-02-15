package by.koshko.crimes.api;

import by.koshko.crimes.entity.AvailableApi;
import by.koshko.crimes.entity.Force;
import by.koshko.crimes.service.ExecutionService;
import by.koshko.crimes.service.exception.ApplicationException;
import by.koshko.crimes.service.exception.ServiceException;
import by.koshko.crimes.service.request.ForceHttpRequestService;
import by.koshko.crimes.service.mapper.StopAndSearchMapper;
import by.koshko.crimes.service.persistence.StopAndSearchPersistenceService;
import by.koshko.crimes.util.CommandLineParameters;
import by.koshko.crimes.util.DateRange;
import by.koshko.crimes.util.ForceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

@Component("stop-and-search-by-force")
public class StopAndSearchByForceApiModule implements ApplicationApiModule {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private ForceHttpRequestService requestService
            = new ForceHttpRequestService(AvailableApi.STOP_AND_SEARCH_BY_FORCE.getApiUrl());
    private StopAndSearchMapper jsonToObjectMapper;
    private StopAndSearchPersistenceService persistenceService;

    @Autowired
    public StopAndSearchByForceApiModule(StopAndSearchMapper jsonToObjectMapper,
                                         StopAndSearchPersistenceService persistenceService) {
        this.jsonToObjectMapper = jsonToObjectMapper;
        this.persistenceService = persistenceService;
    }

    @Override
    public void run(Properties parameters) {
        try {
            List<Force> forces = new ForceLoader().getForces();
            String startDate = parameters.getProperty(CommandLineParameters.FROM_OPTION);
            String endDate = parameters.getProperty(CommandLineParameters.TO_OPTION);
            DateRange dateRange = DateRange.build(startDate, endDate);
            new ExecutionService<>(requestService, jsonToObjectMapper, persistenceService)
                    .execute(forces, dateRange);
        } catch (ServiceException | ApplicationException e) {
            logger.error(e.getMessage());
        }
    }
}
