package by.koshko.crimes.service.persistence;

import by.koshko.crimes.dao.impl.StopAndSearchDao;
import by.koshko.crimes.entity.StopAndSearch;
import by.koshko.crimes.service.PersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StopAndSearchPersistenceService implements PersistenceService<StopAndSearch> {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private StopAndSearchDao stopAndSearchDao;
    private OutcomeObjectPersistenceService outcomeObjectPersistenceService;
    private LocationPersistenceService locationPersistenceService;

    @Autowired
    public StopAndSearchPersistenceService(StopAndSearchDao stopAndSearchDao,
                                           OutcomeObjectPersistenceService outcomeObjectPersistenceService,
                                           LocationPersistenceService locationPersistenceService) {
        this.stopAndSearchDao = stopAndSearchDao;
        this.outcomeObjectPersistenceService = outcomeObjectPersistenceService;
        this.locationPersistenceService = locationPersistenceService;
    }

    @Override
    public void save(StopAndSearch stopAndSearch) {
        Optional.ofNullable(stopAndSearch)
                .ifPresent((object) -> {
                    locationPersistenceService.save(object.getLocation());
                    outcomeObjectPersistenceService.save(object.getOutcome_object());
                    stopAndSearchDao.save(stopAndSearch);
                });
    }
}
