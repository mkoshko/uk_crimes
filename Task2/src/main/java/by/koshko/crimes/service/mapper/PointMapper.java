package by.koshko.crimes.service.mapper;

import by.koshko.crimes.entity.Point;
import by.koshko.crimes.service.MappingException;
import by.koshko.crimes.service.RequestDataMapper;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PointMapper implements RequestDataMapper<Point> {

    private static final int NAME_INDEX = 0;
    private static final int LONGITUDE_INDEX = 1;
    private static final int LATITUDE_INDEX = 2;

    public Point map(String coordinates) throws MappingException {
        if (coordinates == null) {
            throw new MappingException("Empty string.");
        }
        List<String> params = Arrays.asList(coordinates.split(","));
        checkParameters(params);
        return createPoint(params);
    }

    private void checkParameters(List<String> params) throws MappingException {
        if (params.size() != 3) {
            throw new MappingException("Invalid number of parameters, expected 3 but found "
                    + params.size() + ".");
        }
        if (isNotParsable(params.get(LONGITUDE_INDEX))) {
            throw new MappingException("Invalid longitude value, expected double but found "
                    + params.get(LONGITUDE_INDEX) + ".");
        }
        if (isNotParsable(params.get(LATITUDE_INDEX))) {
            throw new MappingException("Invalid latitude value, expected double but found "
                    + params.get(LATITUDE_INDEX) + ".");
        }
    }

    private boolean isNotParsable(String value) {
        return !NumberUtils.isParsable(value);
    }

    private Point createPoint(List<String> params) {
        double longitude = Double.parseDouble(params.get(LONGITUDE_INDEX));
        double latitude = Double.parseDouble(params.get(LATITUDE_INDEX));
        return new Point(params.get(NAME_INDEX), longitude, latitude);
    }

}
