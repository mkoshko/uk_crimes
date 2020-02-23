package by.koshko.crimes.dao;

import by.koshko.crimes.model.OutcomeObject;

public interface OutcomeObjectDao extends Dao<OutcomeObject> {

    long findId(String identity);
}
