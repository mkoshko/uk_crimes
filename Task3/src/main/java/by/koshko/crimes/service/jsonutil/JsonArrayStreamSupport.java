package by.koshko.crimes.service.jsonutil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public final class JsonArrayStreamSupport {

    private JsonArrayStreamSupport() {
    }

    public static Stream<JSONObject> toStream(JSONArray jsonArray) {
        return StreamSupport.stream(new IterableJsonArray(jsonArray).spliterator(), false);
    }
}
