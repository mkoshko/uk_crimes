package by.koshko.crimes.api;

import by.koshko.crimes.model.AvailableApi;
import by.koshko.crimes.service.ExecutionService;
import by.koshko.crimes.service.exception.ApplicationException;
import by.koshko.crimes.service.impl.JsonArrayHandlerImpl;
import by.koshko.crimes.service.jsonutil.IterableJsonArray;
import by.koshko.crimes.service.mapper.ForceMapper;
import by.koshko.crimes.service.mapper.StopAndSearchMapper;
import by.koshko.crimes.service.persistence.StopAndSearchPersistenceService;
import by.koshko.crimes.service.request.ForceBasedRequestUrlBuilder;
import by.koshko.crimes.service.request.HttpRequestService;
import by.koshko.crimes.service.request.RequestService;
import by.koshko.crimes.util.CommandLineParameters;
import by.koshko.crimes.util.DateRange;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component("stop-and-search-by-force")
public class StopAndSearchByForceApiModule implements ApplicationApiModule {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private String forces = "https://data.police.uk/api/forces";
    private String url = AvailableApi.STOP_AND_SEARCH_BY_FORCE.getApiUrl();
    private StopAndSearchMapper jsonToModelMapper;
    private StopAndSearchPersistenceService persistenceService;
    private ForceMapper forceMapper = new ForceMapper();

    @Autowired
    public StopAndSearchByForceApiModule(StopAndSearchMapper jsonToModelMapper,
                                         StopAndSearchPersistenceService persistenceService) {
        this.jsonToModelMapper = jsonToModelMapper;
        this.persistenceService = persistenceService;
    }

    @Override
    public void run(Properties parameters) {
        List<String> forces = loadForces();
        String startDate = parameters.getProperty(CommandLineParameters.FROM_OPTION);
        String endDate = parameters.getProperty(CommandLineParameters.TO_OPTION);
        DateRange dateRange = DateRange.build(startDate, endDate);
        new ExecutionService<>(
                new JsonArrayHandlerImpl<>(jsonToModelMapper, persistenceService),
                new RequestService<>(new ForceBasedRequestUrlBuilder(url))
        ).execute(forces, dateRange);
    }

    private List<String> loadForces() {
        try {
            String response = new HttpRequestService().sendRequest(forces);
            return StreamSupport.stream(new IterableJsonArray(new JSONArray(response)).spliterator(), false)
                    .map(forceMapper::map)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new ApplicationException("Cannot load list of available forces.", e);
        }
    }
}
