package by.koshko.crimes.dao.mapper;

import by.koshko.crimes.entity.CrimeWithSpecOutcome;
import org.codejargon.fluentjdbc.api.query.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CrimesWithSpecOutcomeMapper implements Mapper<CrimeWithSpecOutcome> {

    @Override
    public CrimeWithSpecOutcome map(ResultSet rs) throws SQLException {
        return new CrimeWithSpecOutcome(
                rs.getLong(1),
                rs.getString(2),
                rs.getString(3),
                rs.getInt(4),
                rs.getFloat(5));
    }
}
