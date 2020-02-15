package by.koshko.crimes.dao.mapper;

import by.koshko.crimes.entity.StopSearchStatByEthnicity;
import org.codejargon.fluentjdbc.api.query.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StopSearchStatByEthnicityMapper implements Mapper<StopSearchStatByEthnicity> {

    @Override
    public StopSearchStatByEthnicity map(ResultSet rs) throws SQLException {
        return new StopSearchStatByEthnicity(
                rs.getString(1),
                rs.getFloat(2),
                rs.getFloat(3),
                rs.getFloat(4),
                rs.getString(5)
        );
    }
}
