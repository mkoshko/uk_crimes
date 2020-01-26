package by.koshko.crimes.entity;

public class Crime {

    private long id;
    private String category;
    private String context;
    private String location_type;
    private String persistent_id;
    private String location_subtype;
    private String month;
    private OutcomeStatus outcome_status;
    private Location location;

    public Crime() {
    }

    public Crime(Builder builder) {
        this.id = builder.id;
        this.category = builder.category;
        this.context = builder.context;
        this.location_type = builder.location_type;
        this.persistent_id = builder.persistent_id;
        this.location_subtype = builder.location_subtype;
        this.month = builder.month;
        this.outcome_status = builder.outcome_status;
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

    public String getLocation_type() {
        return location_type;
    }

    public void setLocation_type(String location_type) {
        this.location_type = location_type;
    }

    public OutcomeStatus getOutcome_status() {
        return outcome_status;
    }

    public void setOutcome_status(OutcomeStatus outcome_status) {
        this.outcome_status = outcome_status;
    }

    public String getPersistent_id() {
        return persistent_id;
    }

    public void setPersistent_id(String persistent_id) {
        this.persistent_id = persistent_id;
    }

    public String getLocation_subtype() {
        return location_subtype;
    }

    public void setLocation_subtype(String location_subtype) {
        this.location_subtype = location_subtype;
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
        private String location_type;
        private String persistent_id;
        private String location_subtype;
        private String month;
        private OutcomeStatus outcome_status;
        private Location location;

        public Builder(long id, String category, String location_type, String month, Location location) {
            this.id = id;
            this.category = category;
            this.location_type = location_type;
            this.month = month;
            this.location = location;
        }

        public Builder setContext(String context) {
            this.context = context;
            return this;
        }

        public Builder setPersistent_id(String persistent_id) {
            this.persistent_id = persistent_id;
            return this;
        }

        public Builder setLocation_subtype(String location_subtype) {
            this.location_subtype = location_subtype;
            return this;
        }

        public Builder setOutcome_status(OutcomeStatus outcome_status) {
            this.outcome_status = outcome_status;
            return this;
        }

        public Crime build() {
            return new Crime(this);
        }
    }
}
