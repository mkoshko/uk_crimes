package by.koshko.crimes.service.persistence;

import by.koshko.crimes.dao.OutcomeObjectDao;
import by.koshko.crimes.model.OutcomeObject;
import by.koshko.crimes.service.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutcomeObjectPersistenceService implements PersistenceService<OutcomeObject> {

    private OutcomeObjectDao outcomeObjectDao;

    @Autowired
    public OutcomeObjectPersistenceService(OutcomeObjectDao outcomeObjectDao) {
        this.outcomeObjectDao = outcomeObjectDao;
    }

    @Override
    public void save(OutcomeObject outcomeObject) {
        if (outcomeObject != null) {
            saveOrGetExisting(outcomeObject);
        }
    }

    private void saveOrGetExisting(OutcomeObject outcomeObject) {
        long id = outcomeObjectDao.findId(outcomeObject.getIdentity());
        if (id == 0) {
            id = outcomeObjectDao.save(outcomeObject);
        }
        outcomeObject.setId(id);
    }
}
