package by.koshko.crimes.service.request;

import by.koshko.crimes.service.HttpRequestUrlBuilder;

public class ForceBasedRequestUrlBuilder implements HttpRequestUrlBuilder<String> {

    private final String url;

    public ForceBasedRequestUrlBuilder(String url) {
        this.url = url;
    }

    @Override
    public String buildRequestUrl(String model, String date) {
        StringBuilder request = new StringBuilder(url)
                .append("?")
                .append("force=").append(model);
        if (date != null) {
            request.append("&").append("date=").append(date);
        }
        return request.toString();
    }
}
