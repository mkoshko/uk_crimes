package by.koshko.crimes.service.builder;

import by.koshko.crimes.entity.Point;
import by.koshko.crimes.entity.StopAndSearch;
import by.koshko.crimes.service.ExecutionService;
import by.koshko.crimes.service.ExecutorServiceBuilder;
import by.koshko.crimes.service.PersistenceService;
import by.koshko.crimes.service.impl.PointHttpRequestService;
import by.koshko.crimes.service.mapper.PointMapper;
import by.koshko.crimes.service.mapper.StopAndSearchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("stop-and-search-by-area")
public class StopAndSearchByAreaExecutorServiceBuilder implements ExecutorServiceBuilder {

    private static final String REQUEST_URL = "https://data.police.uk/api/stops-street";
    private PointMapper pointMapper;
    private PointHttpRequestService requestService;
    private StopAndSearchMapper stopAndSearchMapper;
    private PersistenceService<StopAndSearch> persistenceService;

    @Autowired
    public StopAndSearchByAreaExecutorServiceBuilder(PointMapper pointMapper,
                                                     StopAndSearchMapper stopAndSearchMapper,
                                                     PersistenceService<StopAndSearch> persistenceService) {
        this.pointMapper = pointMapper;
        this.stopAndSearchMapper = stopAndSearchMapper;
        this.persistenceService = persistenceService;
        this.requestService = new PointHttpRequestService(REQUEST_URL);
    }

    @Override
    public ExecutionService<StopAndSearch, Point> build() {
        return new ExecutionService<>(pointMapper, requestService, stopAndSearchMapper, persistenceService);
    }
}
