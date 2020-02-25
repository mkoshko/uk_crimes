package by.koshko.crimes.service.request;

import by.koshko.crimes.service.HttpRequestUrlBuilder;
import by.koshko.crimes.service.exception.ServiceException;

import java.io.IOException;

public class RequestService<T> {

    private final HttpRequestUrlBuilder<T> httpRequestUrlBuilder;
    private final HttpRequestService httpRequestService;

    public RequestService(HttpRequestUrlBuilder<T> httpRequestUrlBuilder) {
        this.httpRequestUrlBuilder = httpRequestUrlBuilder;
        httpRequestService = new HttpRequestService();
    }

    public String sendRequest(T model, String date) throws ServiceException {
        String url = httpRequestUrlBuilder.buildRequestUrl(model, date);
        try {
            return httpRequestService.sendRequest(url);
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
