package by.koshko.crimes.dao.impl;

import by.koshko.crimes.dao.Dao;
import by.koshko.crimes.entity.Street;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public class StreetDao implements Dao<Street> {

    private static final String INSERT_QUERY = "INSERT INTO street (id, name) VALUES (?, ?) ON CONFLICT DO NOTHING;";
    private FluentJdbc fluentJdbc;

    @Autowired
    public StreetDao(FluentJdbc fluentJdbc) {
        this.fluentJdbc = fluentJdbc;
    }

    @Override
    public long save(Street street) {
        return fluentJdbc.query()
                .update(INSERT_QUERY)
                .params(Arrays.asList(street.getId(), street.getName()))
                .run()
                .affectedRows();
    }
}
