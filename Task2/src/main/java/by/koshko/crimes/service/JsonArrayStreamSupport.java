package by.koshko.crimes.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class JsonArrayStreamSupport {

    public Stream<JSONObject> toParallelStream(JSONArray jsonArray) {
        return StreamSupport.stream(new IterableJsonArray(jsonArray).spliterator(), true);
    }

    public Stream<JSONObject> toStream(JSONArray jsonArray) {
        return StreamSupport.stream(new IterableJsonArray(jsonArray).spliterator(), false);
    }
}
