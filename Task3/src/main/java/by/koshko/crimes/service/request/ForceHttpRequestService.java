package by.koshko.crimes.service.request;

import by.koshko.crimes.entity.Force;
import by.koshko.crimes.service.HttpRequestService;

public class ForceHttpRequestService extends AbstractHttpRequestService<Force> implements HttpRequestService<Force> {

    public ForceHttpRequestService(String requestUrl) {
        super(requestUrl);
    }

    @Override
    String buildRequestParameters(Force force, String... additionalParams) {
        StringBuilder builder = new StringBuilder("?");
        builder.append("force=").append(force.getId());
        if (additionalParams.length == 1) {
            builder.append("&").append("date=").append(additionalParams[0]);
        }
        return builder.toString();
    }
}
