package by.koshko.crimes.dao.impl;

import by.koshko.crimes.dao.OutcomeStatusDao;
import by.koshko.crimes.entity.OutcomeStatus;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OutcomeStatusDaoImpl implements OutcomeStatusDao {

    private static final String INSERT_QUERY
            = "INSERT INTO outcome_status (category_name_id, date) VALUES (?, ?)"
            + " ON CONFLICT DO NOTHING RETURNING id;";
    private static final String FIND_ID_QUERY
            = "SELECT id "
            + "FROM outcome_status "
            + "WHERE category_name_id=? AND date=?;";
    private FluentJdbc fluentJdbc;

    @Autowired
    public OutcomeStatusDaoImpl(FluentJdbc fluentJdbc) {
        this.fluentJdbc = fluentJdbc;
    }

    @Override
    public long save(OutcomeStatus outcomeStatus) {
        return fluentJdbc.query()
                .update(INSERT_QUERY)
                .params(outcomeStatus.getCategoryNameId(), outcomeStatus.getDate())
                .runFetchGenKeys(Mappers.singleLong())
                .firstKey()
                .orElse(0L);
    }

    @Override
    public long findId(long categoryNameId, String date) {
        return fluentJdbc.query()
                .select(FIND_ID_QUERY)
                .params(categoryNameId, date)
                .firstResult(Mappers.singleLong())
                .orElse(0L);
    }
}
