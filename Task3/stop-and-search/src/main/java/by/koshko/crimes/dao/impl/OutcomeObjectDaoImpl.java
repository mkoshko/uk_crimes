package by.koshko.crimes.dao.impl;

import by.koshko.crimes.dao.OutcomeObjectDao;
import by.koshko.crimes.model.OutcomeObject;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OutcomeObjectDaoImpl implements OutcomeObjectDao {

    private static final String INSERT_QUERY
            = "INSERT INTO outcome_object (identity, name) VALUES (?, ?) ON CONFLICT DO NOTHING RETURNING id";
    private static final String GET_BY_ID_QUERY
            = "SELECT id FROM outcome_object WHERE identity=?;";
    private FluentJdbc fluentJdbc;

    @Autowired
    public OutcomeObjectDaoImpl(FluentJdbc fluentJdbc) {
        this.fluentJdbc = fluentJdbc;
    }

    @Override
    public long save(OutcomeObject outcomeObject) {
        return fluentJdbc.query()
                .update(INSERT_QUERY)
                .params(paramsToList(outcomeObject))
                .runFetchGenKeys(Mappers.singleLong())
                .firstKey()
                .orElse(0L);
    }

    private List<?> paramsToList(OutcomeObject entity) {
        List<Object> params = new ArrayList<>();
        params.add(entity.getIdentity());
        params.add(entity.getName());
        return params;
    }

    @Override
    public long findId(String identity) {
        return fluentJdbc.query()
                .select(GET_BY_ID_QUERY)
                .params(identity)
                .firstResult(rs -> rs.getLong(1))
                .orElse(0L);
    }
}
