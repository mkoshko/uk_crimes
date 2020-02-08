package by.koshko.crimes.service.builder;

import by.koshko.crimes.entity.Crime;
import by.koshko.crimes.entity.Point;
import by.koshko.crimes.service.ExecutionService;
import by.koshko.crimes.service.ExecutorServiceBuilder;
import by.koshko.crimes.service.PersistenceService;
import by.koshko.crimes.service.impl.PointHttpRequestService;
import by.koshko.crimes.service.mapper.CrimeMapper;
import by.koshko.crimes.service.mapper.PointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("street-level-crimes")
public class StreetCrimeExecutorServiceBuilder implements ExecutorServiceBuilder {

    private static final String REQUEST_URL = "https://data.police.uk/api/crimes-street/all-crime";
    private PointMapper pointMapper;
    private PointHttpRequestService requestService;
    private CrimeMapper crimeMapper;
    private PersistenceService<Crime> persistenceService;

    @Autowired
    public StreetCrimeExecutorServiceBuilder(PointMapper pointMapper,
                                             CrimeMapper crimeMapper,
                                             PersistenceService<Crime> persistenceService) {
        this.pointMapper = pointMapper;
        this.crimeMapper = crimeMapper;
        this.persistenceService = persistenceService;
        this.requestService = new PointHttpRequestService(REQUEST_URL);
    }

    public ExecutionService<Crime, Point> build() {
        return new ExecutionService<>(pointMapper, requestService, crimeMapper, persistenceService);
    }
}
