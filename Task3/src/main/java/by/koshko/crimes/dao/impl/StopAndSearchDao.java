package by.koshko.crimes.dao.impl;

import by.koshko.crimes.dao.Dao;
import by.koshko.crimes.entity.Location;
import by.koshko.crimes.entity.OutcomeObject;
import by.koshko.crimes.entity.StopAndSearch;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class StopAndSearchDao implements Dao<StopAndSearch> {

    private static final String INSERT_QUERY
            = "INSERT INTO stop_and_search VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
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
        params.add(Optional.ofNullable(entity.getType()).orElse(null));
        params.add(entity.isInvolved_person());
        params.add(Optional.ofNullable(entity.getTimestamp()).orElse(null));
        params.add(Optional.ofNullable(entity.getOperation_name()).orElse(null));
        params.add(Optional.ofNullable(entity.getLocation()).map(Location::getId).orElse(null));
        params.add(Optional.ofNullable(entity.getGender()).orElse(null));
        params.add(Optional.ofNullable(entity.getAge_range()).orElse(null));
        params.add(Optional.ofNullable(entity.getSelf_defined_ethnicity()).orElse(null));
        params.add(Optional.ofNullable(entity.getOfficer_defined_ethnicity()).orElse(null));
        params.add(Optional.ofNullable(entity.getLegislation()).orElse(null));
        params.add(Optional.ofNullable(entity.getObject_of_search()).orElse(null));
        params.add(Optional.ofNullable(entity.getOutcome()).orElse(null));
        params.add(Optional.ofNullable(entity.getOutcome_object()).map(OutcomeObject::getId).orElse(null));
        params.add(Optional.ofNullable(entity.getOutcome_linked_to_object_of_search()).orElse(null));
        params.add(Optional.ofNullable(entity.getRemoval_of_more_than_outer_clothing()).orElse(null));
        return params;
    }
}
