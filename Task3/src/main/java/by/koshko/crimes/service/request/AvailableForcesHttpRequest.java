package by.koshko.crimes.service.request;

public class AvailableForcesHttpRequest extends AbstractHttpRequestService<Object> {

    public AvailableForcesHttpRequest(String requestUrl) {
        super(requestUrl);
    }

    @Override
    String buildRequestParameters(Object coordinate, String... additionalParams) {
        return "";
    }
}
