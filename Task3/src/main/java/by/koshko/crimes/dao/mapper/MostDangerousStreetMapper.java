package by.koshko.crimes.dao.mapper;

import by.koshko.crimes.entity.MostDangerousStreet;
import org.codejargon.fluentjdbc.api.query.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MostDangerousStreetMapper implements Mapper<MostDangerousStreet> {

    @Override
    public MostDangerousStreet map(ResultSet rs) throws SQLException {
        return new MostDangerousStreet(
                    rs.getLong(1),
                    rs.getString(2),
                    rs.getInt(3));
    }
}
