package by.koshko.crimes.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.stream.Stream;

public class JsonArrayHandler<T> {

    private JsonToObjectMapper<T> mapper;
    private PersistenceService<T> persistenceService;

    public JsonArrayHandler(JsonToObjectMapper<T> mapper,
                                PersistenceService<T> persistenceService) {
        this.mapper = mapper;
        this.persistenceService = persistenceService;
    }

    public void process(String jsonArray) throws ServiceException {
        JSONArray array = createJsonArray(jsonArray);
        Stream<JSONObject> jsonObjectStream = JsonArrayStreamSupport.toStream(array);
        jsonObjectStream.map(mapper::map).forEach(persistenceService::save);
    }

    private JSONArray createJsonArray(String json) throws ServiceException {
        try {
            return new JSONArray(json);
        } catch (JSONException e) {
            throw new ServiceException("Cannot process json array. Invalid syntax.", e);
        }
    }
}
