package by.koshko.crimes.dao.mapper;

import by.koshko.crimes.entity.StopSearchCorrelationWithCrime;
import org.codejargon.fluentjdbc.api.query.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StopSearchCorrelationWithCrimeMapper implements Mapper<StopSearchCorrelationWithCrime> {

    @Override
    public StopSearchCorrelationWithCrime map(ResultSet rs) throws SQLException {
        int index = 0;
        return new StopSearchCorrelationWithCrime(
                rs.getLong(++index),
                rs.getString(++index),
                rs.getString(++index),
                rs.getInt(++index),
                rs.getInt(++index),
                rs.getInt(++index),
                rs.getInt(++index),
                rs.getInt(++index),
                rs.getInt(++index)
        );
    }
}
