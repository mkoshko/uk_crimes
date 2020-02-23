package by.koshko.crimes.dao;

import by.koshko.crimes.model.OutcomeStatus;

public interface OutcomeStatusDao extends Dao<OutcomeStatus> {

    long findId(long categoryNameId, String date);
}
