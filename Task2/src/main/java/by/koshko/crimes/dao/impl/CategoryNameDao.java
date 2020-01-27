package by.koshko.crimes.dao.impl;

import by.koshko.crimes.dao.Dao;
import by.koshko.crimes.entity.OutcomeStatus;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryNameDao implements Dao<OutcomeStatus> {

    private static final String INSERT_QUERY =
            "INSERT INTO category_name (category_name) VALUES (:categoryName)"
                    +" ON CONFLICT (category_name) DO UPDATE SET category_name = :categoryName RETURNING id";
    private FluentJdbc fluentJdbc;

    @Autowired
    public CategoryNameDao(FluentJdbc fluentJdbc) {
        this.fluentJdbc = fluentJdbc;
    }

    @Override
    public long save(OutcomeStatus outcomeStatus) {
        return fluentJdbc.query()
                .update(INSERT_QUERY)
                .namedParam("categoryName", outcomeStatus.getCategory())
                .runFetchGenKeys(Mappers.singleLong())
                .firstKey()
                .orElse(0L);
    }
}
