package by.koshko.crimes.service;

import by.koshko.crimes.dao.impl.CrimeDao;
import by.koshko.crimes.dao.impl.LocationDao;
import by.koshko.crimes.dao.impl.OutcomeStatusDao;
import by.koshko.crimes.dao.impl.StreetDao;
import by.koshko.crimes.entity.Crime;
import by.koshko.crimes.entity.Location;
import by.koshko.crimes.entity.OutcomeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
class StreetCrimesPersistenceService implements PersistenceService<Crime> {

    private CrimeDao crimeDao;
    private LocationDao locationDao;
    private StreetDao streetDao;
    private OutcomeStatusDao outcomeStatusDao;

    @Autowired
    public StreetCrimesPersistenceService(CrimeDao crimeDao, LocationDao locationDao,
                                          StreetDao streetDao, OutcomeStatusDao outcomeStatusDao) {
        this.crimeDao = crimeDao;
        this.locationDao = locationDao;
        this.streetDao = streetDao;
        this.outcomeStatusDao = outcomeStatusDao;
    }

    @Override
    @Transactional
    public void save(Crime crime) {
        if (crime != null) {
            saveStreet(crime);
            saveLocation(crime.getLocation());
            saveOutcomeStatus(crime.getOutcomeStatus());
            crimeDao.save(crime);
        }
    }

    private void saveStreet(Crime crime) {
        Optional.ofNullable(crime)
                .map(Crime::getLocation)
                .map(Location::getStreet)
                .ifPresent(streetDao::save);
    }

    private void saveLocation(Location location) {
        if (location != null) {
            long id = locationDao.save(location);
            location.setId(id);
        }
    }

    private void saveOutcomeStatus(OutcomeStatus outcomeStatus) {
        if (outcomeStatus != null) {
            long id = outcomeStatusDao.save(outcomeStatus);
            outcomeStatus.setId(id);
        }
    }
}
