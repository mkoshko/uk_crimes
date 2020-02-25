package by.koshko.crimes.api;

import by.koshko.crimes.model.AvailableApi;
import by.koshko.crimes.model.Point;
import by.koshko.crimes.model.StopAndSearch;
import by.koshko.crimes.service.JsonToModelMapper;
import by.koshko.crimes.service.PersistenceService;
import by.koshko.crimes.service.request.PointBasedRequestUrlBuilder;
import by.koshko.crimes.service.mapper.PointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("stop-and-search-by-area")
public class StopAndSearchByAreaApiModule extends AbstractApiModule<StopAndSearch, Point> {

    private static String url = AvailableApi.STOP_AND_SEARCH_BY_AREA.getApiUrl();

    @Autowired
    public StopAndSearchByAreaApiModule(PointMapper pointMapper,
                                        JsonToModelMapper<StopAndSearch> jsonToObjectMapper,
                                        PersistenceService<StopAndSearch> persistenceService) {
        super(jsonToObjectMapper, persistenceService, pointMapper, new PointBasedRequestUrlBuilder(url));
    }
}
