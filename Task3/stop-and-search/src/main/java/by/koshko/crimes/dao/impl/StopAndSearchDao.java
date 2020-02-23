package by.koshko.crimes.dao.impl;

import by.koshko.crimes.dao.Dao;
import by.koshko.crimes.model.Location;
import by.koshko.crimes.model.OutcomeObject;
import by.koshko.crimes.model.StopAndSearch;
import by.koshko.crimes.model.Street;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class StopAndSearchDao implements Dao<StopAndSearch> {

    private static final String INSERT_QUERY
            = "INSERT INTO stop_and_search "
            + "(type, involved_person, datetime, operation, operation_name, location_id, gender,"
            + " age_range, self_defined_ethnicity, officer_defined_ethnicity, legislation,"
            + " object_of_search, outcome, outcome_object_id, outcome_linked_to_object_of_search,"
            + " removal_of_more_than_outer_clothing)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private FluentJdbc fluentJdbc;

    @Autowired
    public StopAndSearchDao(FluentJdbc fluentJdbc) {
        this.fluentJdbc = fluentJdbc;
    }

    @Override
    public long save(StopAndSearch stopAndSearch) {
        return fluentJdbc.query()
                .update(INSERT_QUERY)
                .params(paramsToList(stopAndSearch))
                .run()
                .affectedRows();
    }

    private List<?> paramsToList(StopAndSearch entity) {
        List<Object> params = new ArrayList<>();
        params.add(entity.getType());
        params.add(entity.isInvolved_person());
        params.add(entity.getTimestamp());
        params.add(entity.isOperation());
        params.add(entity.getOperation_name());
        params.add(
                Optional.ofNullable(entity.getLocation())
                        .map(Location::getStreet)
                        .map(Street::getId)
                        .orElse(null)
        );
        params.add(entity.getGender());
        params.add(entity.getAge_range());
        params.add(entity.getSelf_defined_ethnicity());
        params.add(entity.getOfficer_defined_ethnicity());
        params.add(entity.getLegislation());
        params.add(entity.getObject_of_search());
        params.add(entity.getOutcome());
        params.add(
                Optional.ofNullable(entity.getOutcome_object())
                .map(OutcomeObject::getId)
                .orElse(null)
        );
        params.add(entity.getOutcome_linked_to_object_of_search());
        params.add(entity.getRemoval_of_more_than_outer_clothing());
        return params;
    }
}
