package by.koshko.crimes.entity;

public class StopSearchStatByEthnicity {

    private String ethnicity;
    private float arrestRate;
    private float noActionRate;
    private float otherActionRate;
    private String objectOfSearch;

    public StopSearchStatByEthnicity(String ethnicity,
                                     float arrestRate,
                                     float noActionRate,
                                     float otherActionRate,
                                     String objectOfSearch) {
        this.ethnicity = ethnicity;
        this.arrestRate = arrestRate;
        this.noActionRate = noActionRate;
        this.otherActionRate = otherActionRate;
        this.objectOfSearch = objectOfSearch;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public float getArrestRate() {
        return arrestRate;
    }

    public float getNoActionRate() {
        return noActionRate;
    }

    public float getOtherActionRate() {
        return otherActionRate;
    }

    public String getObjectOfSearch() {
        return objectOfSearch;
    }

    @Override
    public String toString() {
        return String.format("%s;%f;%f;%f;%s", ethnicity, arrestRate, noActionRate, otherActionRate, objectOfSearch);
    }
}
