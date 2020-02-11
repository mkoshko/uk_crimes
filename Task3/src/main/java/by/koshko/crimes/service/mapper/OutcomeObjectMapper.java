package by.koshko.crimes.service.mapper;

import by.koshko.crimes.entity.OutcomeObject;
import by.koshko.crimes.service.JsonToObjectMapper;
import org.json.JSONObject;

public class OutcomeObjectMapper implements JsonToObjectMapper<OutcomeObject> {

    @Override
    public OutcomeObject map(JSONObject object) {
        if (object != null) {
            return new OutcomeObject(object.optString("id"), object.optString("name"));
        }
        return null;
    }
}
