package by.koshko.crimes.service;

import by.koshko.crimes.entity.Crime;
import by.koshko.crimes.entity.Point;
import by.koshko.crimes.service.mapper.CrimeMapper;
import by.koshko.crimes.service.mapper.PointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("street-level-crimes")
public class StreetCrimeExecutorServiceBuilder implements ExecutorServiceBuilder {

    private PointMapper pointMapper;
    private StreetCrimesHttpRequestService requestService;
    private CrimeMapper crimeMapper;
    private PersistenceService<Crime> persistenceService;

    @Autowired
    public StreetCrimeExecutorServiceBuilder(PointMapper pointMapper,
                                             StreetCrimesHttpRequestService requestService,
                                             CrimeMapper crimeMapper,
                                             PersistenceService<Crime> persistenceService) {
        this.pointMapper = pointMapper;
        this.requestService = requestService;
        this.crimeMapper = crimeMapper;
        this.persistenceService = persistenceService;
    }

    public ExecutorService<Crime, Point> build() {
        return new ExecutorService<>(pointMapper, requestService, crimeMapper, persistenceService);
    }
}
