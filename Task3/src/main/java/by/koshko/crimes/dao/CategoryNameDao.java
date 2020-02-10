package by.koshko.crimes.dao;

import by.koshko.crimes.entity.OutcomeStatus;

public interface CategoryNameDao extends Dao<OutcomeStatus> {

    long findId(String categoryName);
}
