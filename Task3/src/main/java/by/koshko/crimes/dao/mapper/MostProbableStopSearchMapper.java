package by.koshko.crimes.dao.mapper;

import by.koshko.crimes.entity.MostProbableStopSearch;
import org.codejargon.fluentjdbc.api.query.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MostProbableStopSearchMapper implements Mapper<MostProbableStopSearch> {

    @Override
    public MostProbableStopSearch map(ResultSet rs) throws SQLException {
        int index = 0;
        return new MostProbableStopSearch(
                rs.getLong(++index),
                rs.getString(++index),
                rs.getString(++index),
                rs.getString(++index),
                rs.getString(++index),
                rs.getString(++index),
                rs.getString(++index)
        );
    }
}
