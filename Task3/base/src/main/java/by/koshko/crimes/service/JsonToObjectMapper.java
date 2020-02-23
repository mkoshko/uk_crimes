package by.koshko.crimes.service;

import org.json.JSONObject;

public interface JsonToObjectMapper<T> {

    T map(JSONObject object);
}
