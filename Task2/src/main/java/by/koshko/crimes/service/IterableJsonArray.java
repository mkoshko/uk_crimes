package by.koshko.crimes.service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

public class IterableJsonArray implements Iterable<JSONObject> {

    private JSONArray jsonArray;

    public IterableJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    @Override
    public Iterator<JSONObject> iterator() {
        return new JsonIterator();
    }

    private class JsonIterator implements Iterator<JSONObject> {

        private int index;
        private int length;

        public JsonIterator() {
            length = jsonArray.length();
        }

        @Override
        public boolean hasNext() {
            return index < length;
        }

        @Override
        public JSONObject next() {
            return jsonArray.getJSONObject(index++);
        }
    }
}
