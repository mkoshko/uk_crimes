package by.koshko.crimes.dao.impl;

import by.koshko.crimes.dao.Dao;
import by.koshko.crimes.model.Location;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LocationDao implements Dao<Location> {

    private static final String INSERT_QUERY
            = "INSERT INTO location (street_id, latitude, longitude)"
            + " VALUES (?, ?, ?) ON CONFLICT DO NOTHING;";
    private FluentJdbc fluentJdbc;

    @Autowired
    public LocationDao(FluentJdbc fluentJdbc) {
        this.fluentJdbc = fluentJdbc;
    }

    @Override
    public long save(Location location) {
        return fluentJdbc.query()
                .update(INSERT_QUERY)
                .params(location.getStreet().getId(), location.getLatitude(), location.getLongitude())
                .run()
                .affectedRows();
    }
}
