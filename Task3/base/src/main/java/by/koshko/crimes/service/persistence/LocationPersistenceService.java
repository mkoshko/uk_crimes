package by.koshko.crimes.service.persistence;

import by.koshko.crimes.dao.impl.LocationDao;
import by.koshko.crimes.dao.impl.StreetDao;
import by.koshko.crimes.model.Location;
import by.koshko.crimes.service.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationPersistenceService implements PersistenceService<Location> {

    private LocationDao locationDao;
    private StreetDao streetDao;

    @Autowired
    public LocationPersistenceService(LocationDao locationDao, StreetDao streetDao) {
        this.locationDao = locationDao;
        this.streetDao = streetDao;
    }

    @Override
    public void save(Location location) {
        Optional.ofNullable(location)
                .map(Location::getStreet)
                .ifPresent(street -> {
                    streetDao.save(street);
                    locationDao.save(location);
                });
    }
}
