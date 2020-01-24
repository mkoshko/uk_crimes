package by.koshko.crimes.dao;

import by.koshko.crimes.entity.Location;
import org.codejargon.fluentjdbc.api.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class LocationDao extends AbstractDao implements Dao<Location> {

    private static final String INSERT_QUERY = "INSERT INTO location (street_id, latitude, longitude) VALUES (?, ?, ?);";

    @Override
    public void save(Location location) {
        Stream<List<?>> params = Stream.of(Arrays.asList(
                location.getStreet().getId(),
                location.getLatitude(),
                location.getLongitude()
        ));
        Query query = fluentJdbc.query();
        query.batch(INSERT_QUERY)
                .params(params)
                .run();
    }
}
