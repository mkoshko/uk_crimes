package by.koshko.crimes.service.impl;

import by.koshko.crimes.service.HttpRequestService;
import by.koshko.crimes.service.exception.ServiceException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public abstract class AbstractHttpRequestService<T> implements HttpRequestService<T> {

    private String requestUrl;

    public AbstractHttpRequestService(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    @Override
    public String sendRequest(T coordinate, String... additionalParams) throws ServiceException {
        String request = requestUrl + buildRequestParameters(coordinate, additionalParams);
        try {
            System.out.println("Request submission for: " + coordinate);
            HttpURLConnection connection = (HttpURLConnection) new URL(request).openConnection();
            setupConnection(connection);
            return getResponse(connection);
        } catch (IOException e) {
            throw new ServiceException("Request failed. " + e.getMessage());
        }
    }

    private void setupConnection(HttpURLConnection connection) throws ProtocolException {
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
    }

    private String getResponse(HttpURLConnection connection) throws IOException {
        try (InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream())) {
            BufferedReader reader = new BufferedReader(inputStreamReader);
            return reader.readLine();
        }
    }

    abstract String buildRequestParameters(T coordinate, String... additionalParams);
}
