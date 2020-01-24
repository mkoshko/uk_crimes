package by.koshko.crimes.dao;

import by.koshko.crimes.entity.Street;
import org.codejargon.fluentjdbc.api.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class StreetDao extends AbstractDao implements Dao<Street> {

    private static final String INSERT_QUERY = "INSERT INTO street (id, name) VALUES (?, ?);";

    @Override
    public void save(Street street) {
        Stream<List<?>> params = Stream.of(Arrays.asList(street.getId(), street.getName()));
        Query query = fluentJdbc.query();
        query.batch(INSERT_QUERY).params(params).run();
    }
}
