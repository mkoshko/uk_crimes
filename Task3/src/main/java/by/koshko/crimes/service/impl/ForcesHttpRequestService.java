package by.koshko.crimes.service.impl;

public class ForcesHttpRequestService extends AbstractHttpRequestService<Object> {

    public ForcesHttpRequestService(String requestUrl) {
        super(requestUrl);
    }

    @Override
    String buildRequestParameters(Object coordinate, String... additionalParams) {
        return "";
    }
}
