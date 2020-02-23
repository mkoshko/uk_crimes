package by.koshko.crimes.service.mapper;

import by.koshko.crimes.model.Point;
import by.koshko.crimes.service.RequestDataMapper;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PointMapper implements RequestDataMapper<Point> {

    private static final int NAME_INDEX = 0;
    private static final int LONGITUDE_INDEX = 1;
    private static final int LATITUDE_INDEX = 2;
    Logger logger = LoggerFactory.getLogger(PointMapper.class);

    @Override
    public List<Point> map(Stream<String> parameters) {
        return parameters
                .map(this::map)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Point map(String coordinates) {
        if (coordinates == null) {
            return null;
        }
        List<String> params = Arrays.asList(coordinates.split(","));
        if (isParamsValid(params)) {
            return createPoint(params);
        } else {
            return null;
        }
    }

    private boolean isParamsValid(List<String> params) {
        boolean isValid = true;
        if (params.size() != 3) {
            logger.info("Invalid number of parameters, expected 3 but found "
                    + params.size() + ".");
            return false;
        }
        if (isNotParsable(params.get(LONGITUDE_INDEX))) {
            logger.info("Invalid longitude value, expected double but found "
                    + params.get(LONGITUDE_INDEX) + ".");
            isValid = false;
        }
        if (isNotParsable(params.get(LATITUDE_INDEX))) {
            logger.info("Invalid latitude value, expected double but found "
                    + params.get(LATITUDE_INDEX) + ".");
            isValid = false;
        }
        return isValid;
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
