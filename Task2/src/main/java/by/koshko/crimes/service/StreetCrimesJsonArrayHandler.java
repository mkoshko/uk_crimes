package by.koshko.crimes.service;

import by.koshko.crimes.entity.Crime;
import by.koshko.crimes.service.mapper.CrimeMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class StreetCrimesJsonArrayHandler implements JsonArrayHandler {

    private JsonArrayStreamSupport jsonArrayStreamSupport;
    private CrimeMapper crimeMapper;
    private PersistenceService<Crime> persistenceService;

    @Autowired
    public StreetCrimesJsonArrayHandler(JsonArrayStreamSupport converter,
                                        CrimeMapper crimeMapper,
                                        PersistenceService<Crime> persistenceService) {
        this.jsonArrayStreamSupport = converter;
        this.crimeMapper = crimeMapper;
        this.persistenceService = persistenceService;
    }

    @Override
    public void process(String jsonArrayString) throws ServiceException {
        JSONArray jsonArray = createJsonArray(jsonArrayString);
        Stream<JSONObject> jsonObjectParallelStream = jsonArrayStreamSupport.toParallelStream(jsonArray);
        jsonObjectParallelStream.map(crimeMapper::map).forEach(persistenceService::save);
    }

    private JSONArray createJsonArray(String json) throws ServiceException {
        try {
            return new JSONArray(json);
        } catch (JSONException e) {
            throw new ServiceException("Cannot process json array. Invalid syntax.", e);
        }
    }
}
