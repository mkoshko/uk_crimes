package by.koshko.crimes.entity;

public class Crime {

    private long id;
    private String category;
    private String context;
    private String location_type;
    private String outcome_status;
    private String persistent_id;
    private String location_subtype;
    private String month;
    private Location location;

    public Crime() {
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

    public String getOutcome_status() {
        return outcome_status;
    }

    public void setOutcome_status(String outcome_status) {
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
}
