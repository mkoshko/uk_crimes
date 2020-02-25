package by.koshko.crimes.service.mapper;

import by.koshko.crimes.model.OutcomeObject;
import by.koshko.crimes.service.JsonToModelMapper;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class OutcomeModelMapper implements JsonToModelMapper<OutcomeObject> {

    @Override
    public OutcomeObject map(JSONObject object) {
        if (object != null) {
            return new OutcomeObject(object.getString("id"), object.getString("name"));
        }
        return null;
    }
}