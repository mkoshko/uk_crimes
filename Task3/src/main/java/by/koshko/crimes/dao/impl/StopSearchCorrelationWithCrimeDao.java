package by.koshko.crimes.dao.impl;

import by.koshko.crimes.dao.QueryDao;
import by.koshko.crimes.dao.mapper.StopSearchCorrelationWithCrimeMapper;
import by.koshko.crimes.entity.StopSearchCorrelationWithCrime;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("query6")
public class StopSearchCorrelationWithCrimeDao implements QueryDao<StopSearchCorrelationWithCrime> {

    private FluentJdbc fluentJdbc;

    @Autowired
    public StopSearchCorrelationWithCrimeDao(FluentJdbc fluentJdbc) {
        this.fluentJdbc = fluentJdbc;
    }

    @Override
    public List<StopSearchCorrelationWithCrime> get(String sql) {
        return fluentJdbc.query()
                .select(sql)
                .listResult(new StopSearchCorrelationWithCrimeMapper());
    }
}
