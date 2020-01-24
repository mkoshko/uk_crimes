package by.koshko.crimes.dao;

import by.koshko.crimes.entity.Crime;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class CrimeDao extends AbstractDao implements Dao<Crime> {

    private static final String INSERT_QUERY
            = "INSERT INTO crime (id, category, location_type, location_id," +
            " context, outcome_status, persistent_id, location_subtype, month)" +
            " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";

    @Override
    public void save(Crime crime) {
        Stream<List<?>> params = Stream.of(Arrays.asList(crime.getId(), crime.getCategory(),
                crime.getLocation_type(), crime.getLocation().getId(), crime.getContext(),
                crime.getOutcome_status(), crime.getPersistent_id(), crime.getLocation_subtype(),
                crime.getMonth()));
        Query query = fluentJdbc.query();
        query.batch(INSERT_QUERY)
                .params(params)
                .run();
    }
}
