package by.koshko.crimes.service.request;

import by.koshko.crimes.entity.Point;
import by.koshko.crimes.service.HttpRequestService;

public class PointHttpRequestService extends AbstractHttpRequestService<Point> implements HttpRequestService<Point> {

    public PointHttpRequestService(String url) {
        super(url);
    }

    @Override
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
