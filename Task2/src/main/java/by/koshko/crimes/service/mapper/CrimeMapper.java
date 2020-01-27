package by.koshko.crimes.service.mapper;

import by.koshko.crimes.entity.Crime;
import by.koshko.crimes.service.JsonToObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CrimeMapper implements JsonToObjectMapper<Crime> {

    private LocationMapper locationMapper;
    private OutcomeStatusMapper outcomeStatusMapper;

    @Autowired
    public CrimeMapper(LocationMapper locationMapper, OutcomeStatusMapper outcomeStatusMapper) {
        this.locationMapper = locationMapper;
        this.outcomeStatusMapper = outcomeStatusMapper;
    }

    @Override
    public Crime map(JSONObject jsonCrime) {
        return new Crime.Builder(jsonCrime.getLong("id"),
                jsonCrime.getString("category"),
                jsonCrime.getString("location_type"),
                jsonCrime.getString("month"),
                locationMapper.map(jsonCrime.optJSONObject("location")))
                .setContext(jsonCrime.getString("context"))
                .setPersistentId(jsonCrime.getString("persistent_id"))
                .setOutcomeStatus(outcomeStatusMapper.map(jsonCrime.optJSONObject("outcome_status")))
                .build();
    }
}
