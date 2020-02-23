package by.koshko.crimes.api;

import by.koshko.crimes.model.AvailableApi;
import by.koshko.crimes.model.Crime;
import by.koshko.crimes.service.JsonToObjectMapper;
import by.koshko.crimes.service.PersistenceService;
import by.koshko.crimes.service.request.PointBasedRequestUrlBuilder;
import by.koshko.crimes.service.mapper.PointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("street-level-crimes")
public class StreetLevelCrimesApiModule extends AbstractPointBasedApiModule<Crime> {

    @Autowired
    public StreetLevelCrimesApiModule(PointMapper pointMapper,
                                      JsonToObjectMapper<Crime> jsonToObjectMapper,
                                      PersistenceService<Crime> persistenceService) {
        super(pointMapper, jsonToObjectMapper, persistenceService,
                new PointBasedRequestUrlBuilder(AvailableApi.STREET_LEVEL_CRIMES.getApiUrl()));
    }
}
