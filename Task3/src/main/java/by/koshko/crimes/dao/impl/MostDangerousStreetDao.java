package by.koshko.crimes.dao.impl;

import by.koshko.crimes.dao.QueryDao;
import by.koshko.crimes.entity.MostDangerousStreet;
import by.koshko.crimes.dao.mapper.MostDangerousStreetMapper;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MostDangerousStreetDao implements QueryDao<MostDangerousStreet> {

    private FluentJdbc fluentJdbc;

    @Autowired
    public MostDangerousStreetDao(FluentJdbc fluentJdbc) {
        this.fluentJdbc = fluentJdbc;
    }

    @Override
    public List<MostDangerousStreet> get(String sql) {
        return fluentJdbc.query()
                .select(sql)
                .listResult(new MostDangerousStreetMapper());

    }
}
