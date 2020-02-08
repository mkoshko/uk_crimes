package by.koshko.crimes.service.mapper;

import by.koshko.crimes.entity.Street;
import by.koshko.crimes.service.JsonToObjectMapper;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class StreetMapper implements JsonToObjectMapper<Street> {

    private static final String ID = "id";
    private static final String NAME = "name";

    @Override
    public Street map(JSONObject street) {
        if (street != null) {
            return new Street(street.getInt(ID), street.getString(NAME));
        }
        return null;
    }
}
