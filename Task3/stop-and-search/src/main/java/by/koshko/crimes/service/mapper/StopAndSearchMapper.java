package by.koshko.crimes.service.mapper;

import by.koshko.crimes.model.StopAndSearch;
import by.koshko.crimes.service.JsonToModelMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class StopAndSearchMapper implements JsonToModelMapper<StopAndSearch> {

    private LocationMapper locationMapper;
    private OutcomeModelMapper outcomeObjectMapper;

    @Autowired
    public StopAndSearchMapper(LocationMapper locationMapper, OutcomeModelMapper outcomeObjectMapper) {
        this.locationMapper = locationMapper;
        this.outcomeObjectMapper = outcomeObjectMapper;
    }

    @Override
    public StopAndSearch map(JSONObject object) {
        if (object != null) {
            return new StopAndSearch.StopAndSearchBuilder()
                    .setType(object.optString("type"))
                    .setInvolved_person(object.optBoolean("involved_person", false))
                    .setTimestamp(object.optString("datetime") != null
                            ? LocalDateTime.parse(object.getString("datetime"), DateTimeFormatter.ISO_ZONED_DATE_TIME) : null)
                    .setOperation(object.optBoolean("operation", false))
                    .setOperation_name(object.optString("operation_name"))
                    .setLocation(locationMapper.map(object.optJSONObject("location")))
                    .setGender(object.optString("gender"))
                    .setAge_range(object.optString("age_range"))
                    .setSelf_defined_ethnicity(object.optString("self_defined_ethnicity"))
                    .setOfficer_defined_ethnicity(object.optString("officer_defined_ethnicity"))
                    .setLegislation(object.optString("legislation"))
                    .setObject_of_search(object.optString("object_of_search"))
                    .setOutcome(object.optString("outcome"))
                    .setOutcome_linked_to_object_of_search(object.optBoolean("outcome_linked_to_object_of_search"))
                    .setRemoval_of_more_than_outer_clothing(object.optBoolean("removal_of_more_than_outer_clothing"))
                    .setOutcome_object(outcomeObjectMapper.map(object.optJSONObject("outcome_object")))
                    .build();
        }
        return null;
    }
}
