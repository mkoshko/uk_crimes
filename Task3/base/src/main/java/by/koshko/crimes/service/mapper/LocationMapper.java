package by.koshko.crimes.service.mapper;

import by.koshko.crimes.model.Location;
import by.koshko.crimes.service.JsonToModelMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper implements JsonToModelMapper<Location> {

    private static final String STREET = "street";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";

    private StreetMapper streetMapper;

    @Autowired
    public void setStreetMapper(StreetMapper streetMapper) {
        this.streetMapper = streetMapper;
    }

    @Override
    public Location map(JSONObject location) {
        if (location != null) {
            return new Location(streetMapper.map(location.optJSONObject(STREET)),
                    location.getDouble(LATITUDE), location.getDouble(LONGITUDE));
        }
        return null;
    }
}
