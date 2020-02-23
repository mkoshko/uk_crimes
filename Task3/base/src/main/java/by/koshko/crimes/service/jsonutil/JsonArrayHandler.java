package by.koshko.crimes.service.jsonutil;

import by.koshko.crimes.service.JsonToObjectMapper;
import by.koshko.crimes.service.PersistenceService;
import by.koshko.crimes.service.exception.ServiceException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class JsonArrayHandler<T> {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private JsonToObjectMapper<T> mapper;
    private PersistenceService<T> persistenceService;

    public JsonArrayHandler(JsonToObjectMapper<T> mapper,
                                PersistenceService<T> persistenceService) {
        this.mapper = mapper;
        this.persistenceService = persistenceService;
    }

    public void process(String jsonArray) throws ServiceException {
        long startTime = System.currentTimeMillis();
        JSONArray array = createJsonArray(jsonArray);
        Stream<JSONObject> jsonObjectStream = JsonArrayStreamSupport.toStream(array);
        jsonObjectStream.map(mapper::map).forEach(persistenceService::save);
        logger.info("Thread[{}] has finished in {} ms.", Thread.currentThread().getName(),
                System.currentTimeMillis() - startTime);
    }

    private JSONArray createJsonArray(String json) throws ServiceException {
        try {
            return new JSONArray(json);
        } catch (JSONException e) {
            throw new ServiceException("Cannot process json array. Invalid syntax.", e);
        }
    }
}
