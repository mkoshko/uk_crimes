package by.koshko.crimes.entity;

public class StopSearchStatByEthnicity {

    private String ethnicity;
    private int total;
    private float arrestRate;
    private float noActionRate;
    private float otherActionRate;
    private String objectOfSearch;

    public StopSearchStatByEthnicity(String ethnicity, int total, float arrestRate, float noActionRate, float otherActionRate, String objectOfSearch) {
        this.ethnicity = ethnicity;
        this.total = total;
        this.arrestRate = arrestRate;
        this.noActionRate = noActionRate;
        this.otherActionRate = otherActionRate;
        this.objectOfSearch = objectOfSearch;
    }

    @Override
    public String toString() {
        return String.format("%s;%d;%.2f;%.2f;%.2f;%s", ethnicity, total, arrestRate, noActionRate, otherActionRate, objectOfSearch);
    }
}
