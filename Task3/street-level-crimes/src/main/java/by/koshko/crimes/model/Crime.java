package by.koshko.crimes.model;

public class Crime {

    private long id;
    private String category;
    private String context;
    private String locationType;
    private String persistentId;
    private String locationSubtype;
    private String month;
    private OutcomeStatus outcomeStatus;
    private Location location;

    public Crime() {
    }

    public Crime(Builder builder) {
        this.id = builder.id;
        this.category = builder.category;
        this.context = builder.context;
        this.locationType = builder.locationType;
        this.persistentId = builder.persistentId;
        this.locationSubtype = builder.locationSubtype;
        this.month = builder.month;
        this.outcomeStatus = builder.outcomeStatus;
        this.location = builder.location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public OutcomeStatus getOutcomeStatus() {
        return outcomeStatus;
    }

    public void setOutcomeStatus(OutcomeStatus outcomeStatus) {
        this.outcomeStatus = outcomeStatus;
    }

    public String getPersistentId() {
        return persistentId;
    }

    public void setPersistentId(String persistentId) {
        this.persistentId = persistentId;
    }

    public String getLocationSubtype() {
        return locationSubtype;
    }

    public void setLocationSubtype(String locationSubtype) {
        this.locationSubtype = locationSubtype;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public static class Builder {

        private long id;
        private String category;
        private String context;
        private String locationType;
        private String persistentId;
        private String locationSubtype;
        private String month;
        private OutcomeStatus outcomeStatus;
        private Location location;

        public Builder(long id, String category, String locationType, String month, Location location) {
            this.id = id;
            this.category = category;
            this.locationType = locationType;
            this.month = month;
            this.location = location;
        }

        public Builder setContext(String context) {
            this.context = context;
            return this;
        }

        public Builder setPersistentId(String persistentId) {
            this.persistentId = persistentId;
            return this;
        }

        public Builder setLocationSubtype(String locationSubtype) {
            this.locationSubtype = locationSubtype;
            return this;
        }

        public Builder setOutcomeStatus(OutcomeStatus outcomeStatus) {
            this.outcomeStatus = outcomeStatus;
            return this;
        }

        public Crime build() {
            return new Crime(this);
        }
    }
}
