package by.koshko.crimes.service.impl;

import by.koshko.crimes.service.JsonArrayHandler;
import by.koshko.crimes.service.JsonToModelMapper;
import by.koshko.crimes.service.PersistenceService;
import by.koshko.crimes.service.jsonutil.IterableJsonArray;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class JsonArrayHandlerImpl<T> implements JsonArrayHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private JsonToModelMapper<T> mapper;
    private PersistenceService<T> persistenceService;

    public JsonArrayHandlerImpl(JsonToModelMapper<T> mapper,
                                PersistenceService<T> persistenceService) {
        this.mapper = mapper;
        this.persistenceService = persistenceService;
    }

    public void process(String jsonArray) {
        try {
            JSONArray array = new JSONArray(jsonArray);
            Stream<JSONObject> jsonStream = StreamSupport.stream(new IterableJsonArray(array).spliterator(), false);
            jsonStream.map(mapper::map).forEach(persistenceService::save);
        } catch (JSONException e) {
            logger.error("Cannot process json array. Invalid syntax.", e);
        }
    }
}
