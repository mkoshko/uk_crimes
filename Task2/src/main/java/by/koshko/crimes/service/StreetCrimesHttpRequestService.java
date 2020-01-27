package by.koshko.crimes.service;

import by.koshko.crimes.entity.Point;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class StreetCrimesHttpRequestService implements HttpRequestService<Point> {

    private static final String REQUEST_URL = "https://data.police.uk/api/crimes-street/all-crime";

    @Override
    public String sendRequest(Point point, String... additionalParams) throws ServiceException {
        HttpURLConnection connection;
        try {
            URL url = new URL(REQUEST_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            writeParameters(connection, buildRequestParameters(point, additionalParams));
            return getResponse(connection);
        } catch (IOException e) {
            throw new ServiceException("Cannot send request.", e);
        }
    }

    private void writeParameters(HttpURLConnection connection, String parameters) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream())) {
            dataOutputStream.write(parameters.getBytes());
            dataOutputStream.flush();
        }
    }

    private String getResponse(HttpURLConnection connection) throws IOException {
        try (InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream())) {
            BufferedReader reader = new BufferedReader(inputStreamReader);
            return reader.readLine();
        }
    }

    private String buildRequestParameters(Point point, String... additionalParams) {
        StringBuilder builder = new StringBuilder("?");
        builder.append("lat=").append(point.getLatitude());
        builder.append("&").append("lng=").append(point.getLongitude());
        if (additionalParams.length == 1) {
            builder.append("&").append("date=").append(additionalParams[0]);
        }
        return builder.toString();
    }
}
