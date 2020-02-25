package by.koshko.crimes.service.persistence;

import by.koshko.crimes.dao.CategoryNameDao;
import by.koshko.crimes.dao.impl.CrimeDao;
import by.koshko.crimes.dao.impl.OutcomeStatusDaoImpl;
import by.koshko.crimes.model.Crime;
import by.koshko.crimes.model.OutcomeStatus;
import by.koshko.crimes.service.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class CrimesPersistenceService implements PersistenceService<Crime> {

    private CrimeDao crimeDao;
    private LocationPersistenceService locationPersistenceService;
    private OutcomeStatusDaoImpl outcomeStatusDao;
    private CategoryNameDao categoryNameDao;

    @Autowired
    public CrimesPersistenceService(CrimeDao crimeDao,
                                    LocationPersistenceService locationPersistenceService,
                                    OutcomeStatusDaoImpl outcomeStatusDao,
                                    CategoryNameDao categoryNameDao) {
        this.crimeDao = crimeDao;
        this.locationPersistenceService = locationPersistenceService;
        this.outcomeStatusDao = outcomeStatusDao;
        this.categoryNameDao = categoryNameDao;
    }

    @Override
    @Transactional
    public void save(Crime crime) {
        if (crime != null) {
            locationPersistenceService.save(crime.getLocation());
            saveOutcomeStatus(crime.getOutcomeStatus());
            crimeDao.save(crime);
        }
    }

    private void saveOutcomeStatus(OutcomeStatus outcomeStatus) {
        if (outcomeStatus != null) {
            long categoryNameId = saveCategoryNameOrFindExisting(outcomeStatus);
            outcomeStatus.setCategoryNameId(categoryNameId);
            long outcomeStatusId = saveOutcomeStatusOrFindExisting(categoryNameId, outcomeStatus);
            outcomeStatus.setId(outcomeStatusId);
        }
    }

    private long saveCategoryNameOrFindExisting(OutcomeStatus outcomeStatus) {
        long categoryNameId = categoryNameDao.findId(outcomeStatus.getCategory());
        if (categoryNameId != 0) {
            return categoryNameId;
        }
        return categoryNameDao.save(outcomeStatus);
    }

    private long saveOutcomeStatusOrFindExisting(long categoryNameId, OutcomeStatus outcomeStatus) {
        long outcomeStatusId = outcomeStatusDao.findId(categoryNameId, outcomeStatus.getDate());
        if (outcomeStatusId != 0) {
            return outcomeStatusId;
        }
        return outcomeStatusDao.save(outcomeStatus);
    }
}
