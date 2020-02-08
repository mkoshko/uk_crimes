package by.koshko.crimes.service;

import by.koshko.crimes.service.exception.ServiceException;

public interface HttpRequestService<T> {

    String sendRequest(T param, String... additionalParams) throws ServiceException;
}
