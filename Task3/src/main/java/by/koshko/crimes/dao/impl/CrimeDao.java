package by.koshko.crimes.dao.impl;

import by.koshko.crimes.dao.Dao;
import by.koshko.crimes.entity.Crime;
import by.koshko.crimes.entity.Location;
import by.koshko.crimes.entity.OutcomeStatus;
import by.koshko.crimes.entity.Street;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CrimeDao implements Dao<Crime> {

    private static final String INSERT_QUERY
            = "INSERT INTO crime (id, category, location_type, location_id,"
            + " context, outcome_status_id, persistent_id, location_subtype, month)"
            + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT DO NOTHING;";
    private FluentJdbc fluentJdbc;

    @Autowired
    public CrimeDao(FluentJdbc fluentJdbc) {
        this.fluentJdbc = fluentJdbc;
    }

    @Override
    public long save(Crime crime) {
        return fluentJdbc.query()
                .update(INSERT_QUERY)
                .params(crimeParamsToList(crime))
                .run()
                .affectedRows();
    }

    private List<?> crimeParamsToList(Crime crime) {
        List<Object> params = new ArrayList<>();
        params.add(crime.getId());
        params.add(crime.getCategory());
        params.add(crime.getLocationType());
        params.add(Optional.ofNullable(crime.getLocation())
                .map(Location::getStreet)
                .map(Street::getId)
                .orElse(null));
        params.add(crime.getContext());
        params.add(Optional.ofNullable(crime.getOutcomeStatus())
                .map(OutcomeStatus::getId).orElse(null));
        params.add(crime.getPersistentId());
        params.add(crime.getLocationSubtype());
        params.add(crime.getMonth());
        return params;
    }
}
