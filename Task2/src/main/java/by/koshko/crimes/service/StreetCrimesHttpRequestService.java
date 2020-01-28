package by.koshko.crimes.service;

import by.koshko.crimes.entity.Point;
import org.springframework.stereotype.Service;

@Service
public class StreetCrimesHttpRequestService extends AbstractHttpRequestService implements HttpRequestService<Point> {

    private static final String REQUEST_URL = "https://data.police.uk/api/crimes-street/all-crime";

    public StreetCrimesHttpRequestService() {
        super(REQUEST_URL);
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
