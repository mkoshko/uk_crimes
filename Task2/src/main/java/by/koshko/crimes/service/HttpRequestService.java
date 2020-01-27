package by.koshko.crimes.service;

public interface HttpRequestService<T> {

    String sendRequest(T param, String... additionalParams) throws ServiceException;
}
