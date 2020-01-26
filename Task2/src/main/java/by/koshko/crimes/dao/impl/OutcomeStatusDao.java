package by.koshko.crimes.dao.impl;

import by.koshko.crimes.dao.Dao;
import by.koshko.crimes.entity.Category;
import by.koshko.crimes.entity.OutcomeStatus;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OutcomeStatusDao implements Dao<OutcomeStatus> {

    private static final String INSERT_QUERY
            = "INSERT INTO outcome_status (category_name_id, date) VALUES (:categoryNameId, :date)" +
            " on conflict (category_name_id, date) do update set category_name_id = :categoryNameId" +
            " returning id;";
    private FluentJdbc fluentJdbc;

    @Autowired
    public OutcomeStatusDao(FluentJdbc fluentJdbc) {
        this.fluentJdbc = fluentJdbc;
    }

    @Override
    public long save(OutcomeStatus outcomeStatus) {
        int category_id = Category.getCategoryId(outcomeStatus.getCategory());
        return fluentJdbc.query()
                .update(INSERT_QUERY)
                .namedParam("categoryNameId", category_id)
                .namedParam("date", outcomeStatus.getDate())
                .runFetchGenKeys(Mappers.singleLong())
                .firstKey()
                .orElse(0L);
    }
}
