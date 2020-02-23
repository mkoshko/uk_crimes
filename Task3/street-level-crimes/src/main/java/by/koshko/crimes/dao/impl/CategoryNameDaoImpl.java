package by.koshko.crimes.dao.impl;

import by.koshko.crimes.dao.CategoryNameDao;
import by.koshko.crimes.model.OutcomeStatus;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryNameDaoImpl implements CategoryNameDao {

    private static final String INSERT_QUERY
            = "INSERT INTO category_name (category_name) VALUES (?)"
            + " ON CONFLICT DO NOTHING RETURNING id";
    private static final String FIND_ID_QUERY =
            "SELECT id FROM category_name WHERE category_name=?;";
    private FluentJdbc fluentJdbc;


    @Autowired
    public CategoryNameDaoImpl(FluentJdbc fluentJdbc) {
        this.fluentJdbc = fluentJdbc;
    }

    @Override
    public long save(OutcomeStatus outcomeStatus) {
        return fluentJdbc.query()
                .update(INSERT_QUERY)
                .params(outcomeStatus.getCategory())
                .runFetchGenKeys(Mappers.singleLong())
                .firstKey()
                .orElse(0L);
    }

    @Override
    public long findId(String categoryName) {
        return fluentJdbc.query()
                .select(FIND_ID_QUERY)
                .params(categoryName)
                .firstResult(Mappers.singleLong())
                .orElse(0L);
    }
}
