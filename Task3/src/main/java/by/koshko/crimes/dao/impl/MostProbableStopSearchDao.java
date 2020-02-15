package by.koshko.crimes.dao.impl;

import by.koshko.crimes.dao.QueryDao;
import by.koshko.crimes.dao.mapper.MostProbableStopSearchMapper;
import by.koshko.crimes.entity.MostProbableStopSearch;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("query5")
public class MostProbableStopSearchDao implements QueryDao<MostProbableStopSearch> {

    private FluentJdbc fluentJdbc;

    @Autowired
    public MostProbableStopSearchDao(FluentJdbc fluentJdbc) {
        this.fluentJdbc = fluentJdbc;
    }

    @Override
    public List<MostProbableStopSearch> get(String sql) {
        return fluentJdbc.query()
                .select(sql)
                .listResult(new MostProbableStopSearchMapper());
    }
}
