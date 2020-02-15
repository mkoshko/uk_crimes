package by.koshko.crimes.dao.impl;

import by.koshko.crimes.dao.QueryDao;
import by.koshko.crimes.dao.mapper.CrimeVolumeComparisonMapper;
import by.koshko.crimes.entity.CrimeVolumeComparison;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("query2")
public class CrimeVolumeDao implements QueryDao<CrimeVolumeComparison> {

    private FluentJdbc fluentJdbc;

    @Autowired
    public CrimeVolumeDao(FluentJdbc fluentJdbc) {
        this.fluentJdbc = fluentJdbc;
    }

    @Override
    public List<CrimeVolumeComparison> get(String sql) {
        return fluentJdbc.query()
                .select(sql)
                .listResult(new CrimeVolumeComparisonMapper());
    }
}
