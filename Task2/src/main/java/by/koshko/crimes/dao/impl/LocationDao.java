package by.koshko.crimes.dao.impl;

import by.koshko.crimes.dao.Dao;
import by.koshko.crimes.entity.Location;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LocationDao implements Dao<Location> {

    private static final String INSERT_QUERY
            = "INSERT INTO location (street_id, latitude, longitude) VALUES (:streetId, :latitude, :longitude)" +
            " on conflict (latitude, longitude) do update set latitude = :latitude returning id;";
    private FluentJdbc fluentJdbc;

    @Autowired
    public LocationDao(FluentJdbc fluentJdbc) {
        this.fluentJdbc = fluentJdbc;
    }

    @Override
    public long save(Location location) {
        return fluentJdbc.query()
                .update(INSERT_QUERY)
                .namedParam("streetId", location.getStreet() != null ? location.getStreet().getId() : null)
                .namedParam("latitude", location.getLatitude())
                .namedParam("longitude", location.getLongitude())
                .runFetchGenKeys(Mappers.singleLong())
                .firstKey()
                .orElse(0L);
    }
}
