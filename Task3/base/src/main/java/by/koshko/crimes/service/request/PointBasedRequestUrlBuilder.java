package by.koshko.crimes.service.request;

import by.koshko.crimes.model.Point;
import by.koshko.crimes.service.HttpRequestUrlBuilder;

public class PointBasedRequestUrlBuilder implements HttpRequestUrlBuilder<Point> {

    private final String url;

    public PointBasedRequestUrlBuilder(String url) {
        this.url = url;
    }

    public String buildRequestUrl(Point point, String date) {
        StringBuilder request = new StringBuilder(url)
                .append("?")
                .append("lat=").append(point.getLatitude())
                .append("&")
                .append("lng=").append(point.getLongitude())
                .append("&");
        if (date != null) {
            request.append("date=").append(date);
        }
        return request.toString();
    }
}
