package by.koshko.crimes.service;

import by.koshko.crimes.model.Point;

public interface HttpRequestUrlBuilder<T> {

    String buildRequestUrl(T model, String date);
}
