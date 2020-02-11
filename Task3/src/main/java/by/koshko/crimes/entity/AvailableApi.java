package by.koshko.crimes.entity;

public enum AvailableApi {

    STREET_LEVEL_CRIMES("https://data.police.uk/api/crimes-street/all-crime"),
    STOP_AND_SEARCH_BY_AREA("https://data.police.uk/api/stops-street");

    AvailableApi(String url) {
        apiUrl = url;
    }

    private String apiUrl;

    public String getApiUrl() {
        return apiUrl;
    }
}
