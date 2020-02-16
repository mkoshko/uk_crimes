package by.koshko.crimes.dao.mapper;

import by.koshko.crimes.entity.StopSearchStatByEthnicity;
import org.codejargon.fluentjdbc.api.query.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StopSearchStatByEthnicityMapper implements Mapper<StopSearchStatByEthnicity> {

    @Override
    public StopSearchStatByEthnicity map(ResultSet rs) throws SQLException {
        int index = 0;
        return new StopSearchStatByEthnicity(
                rs.getString(++index),
                rs.getInt(++index),
                rs.getFloat(++index),
                rs.getFloat(++index),
                rs.getFloat(++index),
                rs.getString(++index)
        );
    }
}
