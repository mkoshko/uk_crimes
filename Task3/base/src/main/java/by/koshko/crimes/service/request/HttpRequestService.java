package by.koshko.crimes.service.request;

import by.koshko.crimes.service.exception.ServiceException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class HttpRequestService {

    public String sendRequest(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        setupConnection(connection);
        return getResponse(connection);
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
}
