package by.koshko.crimes.service.mapper;

import by.koshko.crimes.entity.Force;
import by.koshko.crimes.service.JsonToObjectMapper;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class ForceMapper implements JsonToObjectMapper<Force> {

    @Override
    public Force map(JSONObject object) {
        return new Force(object.getString("id"));
    }
}
