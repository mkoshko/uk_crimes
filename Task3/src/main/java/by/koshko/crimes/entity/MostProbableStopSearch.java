package by.koshko.crimes.entity;

public class MostProbableStopSearch {

    private long id;
    private String name;
    private String ageRange;
    private String gender;
    private String ethnicity;
    private String objectOfSearch;
    private String outcome;

    public MostProbableStopSearch(long id,
                                  String name,
                                  String ageRange,
                                  String gender,
                                  String ethnicity,
                                  String objectOfSearch,
                                  String outcome) {
        this.id = id;
        this.name = name;
        this.ageRange = ageRange;
        this.gender = gender;
        this.ethnicity = ethnicity;
        this.objectOfSearch = objectOfSearch;
        this.outcome = outcome;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public String getGender() {
        return gender;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public String getObjectOfSearch() {
        return objectOfSearch;
    }

    public String getOutcome() {
        return outcome;
    }

    @Override
    public String toString() {
        return String.format("%d;%s;%s;%s;%s;%s;%s;", id, name, ageRange, gender, ethnicity, objectOfSearch, outcome);
    }
}
