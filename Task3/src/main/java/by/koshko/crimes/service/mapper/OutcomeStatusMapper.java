package by.koshko.crimes.service.mapper;

import by.koshko.crimes.entity.OutcomeStatus;
import by.koshko.crimes.service.JsonToObjectMapper;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class OutcomeStatusMapper implements JsonToObjectMapper<OutcomeStatus> {

    @Override
    public OutcomeStatus map(JSONObject outcomeStatus) {
        if (outcomeStatus != null) {
            return new OutcomeStatus(outcomeStatus.getString("category"),
                    outcomeStatus.getString("date"));
        } else {
            return null;
        }
    }
}
