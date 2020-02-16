package by.koshko.crimes.dao.mapper;

import by.koshko.crimes.entity.CrimeVolumeComparison;
import org.codejargon.fluentjdbc.api.query.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CrimeVolumeComparisonMapper implements Mapper<CrimeVolumeComparison> {

    @Override
    public CrimeVolumeComparison map(ResultSet rs) throws SQLException {
        return new CrimeVolumeComparison(
                rs.getString(1),
                rs.getString(2),
                rs.getInt(3),
                rs.getInt(4),
                rs.getInt(5),
                rs.getFloat(6)
        );
    }
}
