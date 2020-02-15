package by.koshko.crimes.dao.impl;

import by.koshko.crimes.dao.QueryDao;
import by.koshko.crimes.dao.mapper.CrimesWithSpecOutcomeMapper;
import by.koshko.crimes.entity.CrimeWithSpecOutcome;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("query3")
public class CrimesWithSpecOutcomeDao implements QueryDao<CrimeWithSpecOutcome> {

    private FluentJdbc fluentJdbc;

    @Autowired
    public CrimesWithSpecOutcomeDao(FluentJdbc fluentJdbc) {
        this.fluentJdbc = fluentJdbc;
    }

    @Override
    public List<CrimeWithSpecOutcome> get(String sql) {
        return fluentJdbc.query()
                .select(sql)
                .listResult(new CrimesWithSpecOutcomeMapper());
    }
}
