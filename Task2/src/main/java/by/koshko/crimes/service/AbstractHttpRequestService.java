package by.koshko.crimes.service;

import by.koshko.crimes.entity.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class AbstractHttpRequestService implements HttpRequestService<Point> {

    private Logger logger = LoggerFactory.getLogger(StreetCrimesHttpRequestService.class);
    private String requestUrl;

    public AbstractHttpRequestService(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    @Override
    public String sendRequest(Point point, String... additionalParams) throws ServiceException {
        logger.info("Requesting information for " + point.getName() + ".");
        try {
            URL url = new URL(this.requestUrl + buildRequestParameters(point, additionalParams));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            return getResponse(connection);
        } catch (IOException e) {
            throw new ServiceException("Cannot send request." + e.getMessage(), e);
        }
    }

    private String getResponse(HttpURLConnection connection) throws IOException {
        try (InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream())) {
            BufferedReader reader = new BufferedReader(inputStreamReader);
            return reader.readLine();
        }
    }

    String buildRequestParameters(Point point, String... additionalParams) {
        StringBuilder builder = new StringBuilder("?");
        builder.append("lat=").append(point.getLatitude());
        builder.append("&").append("lng=").append(point.getLongitude());
        if (additionalParams.length == 1) {
            builder.append("&").append("date=").append(additionalParams[0]);
        }
        return builder.toString();
    }
}
