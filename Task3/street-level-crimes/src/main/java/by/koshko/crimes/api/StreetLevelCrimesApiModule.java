package by.koshko.crimes.api;

import by.koshko.crimes.model.AvailableApi;
import by.koshko.crimes.model.Crime;
import by.koshko.crimes.model.Point;
import by.koshko.crimes.service.JsonToModelMapper;
import by.koshko.crimes.service.PersistenceService;
import by.koshko.crimes.service.request.PointBasedRequestUrlBuilder;
import by.koshko.crimes.service.mapper.PointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("street-level-crimes")
public class StreetLevelCrimesApiModule extends AbstractApiModule<Crime, Point> {

    private static final String URL = AvailableApi.STREET_LEVEL_CRIMES.getApiUrl();

    @Autowired
    public StreetLevelCrimesApiModule(PointMapper pointMapper,
                                      JsonToModelMapper<Crime> jsonToObjectMapper,
                                      PersistenceService<Crime> persistenceService) {
        super(jsonToObjectMapper, persistenceService, pointMapper, new PointBasedRequestUrlBuilder(URL));
    }
}
