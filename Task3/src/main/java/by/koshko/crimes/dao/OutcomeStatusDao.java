package by.koshko.crimes.dao;

import by.koshko.crimes.entity.OutcomeStatus;

public interface OutcomeStatusDao extends Dao<OutcomeStatus> {

    long findId(long categoryNameId, String date);
}
