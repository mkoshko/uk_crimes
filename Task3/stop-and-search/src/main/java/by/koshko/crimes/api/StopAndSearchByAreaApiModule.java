package by.koshko.crimes.api;

import by.koshko.crimes.model.AvailableApi;
import by.koshko.crimes.model.StopAndSearch;
import by.koshko.crimes.service.JsonToObjectMapper;
import by.koshko.crimes.service.PersistenceService;
import by.koshko.crimes.service.request.PointHttpRequestService;
import by.koshko.crimes.service.mapper.PointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("stop-and-search-by-area")
public class StopAndSearchByAreaApiModule extends AbstractPointBasedApiModule<StopAndSearch> {

    @Autowired
    public StopAndSearchByAreaApiModule(PointMapper pointMapper,
                                        JsonToObjectMapper<StopAndSearch> jsonToObjectMapper,
                                        PersistenceService<StopAndSearch> persistenceService) {
        super(pointMapper, jsonToObjectMapper, persistenceService,
                new PointHttpRequestService(AvailableApi.STOP_AND_SEARCH_BY_AREA.getApiUrl()));
    }
}
