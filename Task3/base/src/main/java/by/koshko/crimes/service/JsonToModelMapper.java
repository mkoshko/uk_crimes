package by.koshko.crimes.service;

import org.json.JSONObject;

public interface JsonToModelMapper<T> {

    T map(JSONObject object);
}
