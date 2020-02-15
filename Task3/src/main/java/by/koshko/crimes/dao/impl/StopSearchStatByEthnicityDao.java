package by.koshko.crimes.dao.impl;

import by.koshko.crimes.dao.QueryDao;
import by.koshko.crimes.dao.mapper.StopSearchStatByEthnicityMapper;
import by.koshko.crimes.entity.StopSearchStatByEthnicity;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("query4")
public class StopSearchStatByEthnicityDao implements QueryDao<StopSearchStatByEthnicity> {

    private FluentJdbc fluentJdbc;

    @Autowired
    public StopSearchStatByEthnicityDao(FluentJdbc fluentJdbc) {
        this.fluentJdbc = fluentJdbc;
    }

    @Override
    public List<StopSearchStatByEthnicity> get(String sql) {
        return fluentJdbc.query()
                .select(sql)
                .listResult(new StopSearchStatByEthnicityMapper());
    }
}
