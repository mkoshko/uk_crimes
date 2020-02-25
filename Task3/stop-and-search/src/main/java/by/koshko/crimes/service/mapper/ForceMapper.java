package by.koshko.crimes.service.mapper;

import by.koshko.crimes.service.JsonToModelMapper;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class ForceMapper implements JsonToModelMapper<String> {

    @Override
    public String map(JSONObject object) {
        return object.getString("id");
    }
}
