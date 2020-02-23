package by.koshko.crimes.dao;

import by.koshko.crimes.model.OutcomeStatus;

public interface CategoryNameDao extends Dao<OutcomeStatus> {

    long findId(String categoryName);
}
