package by.koshko.crimes.entity;

public class OutcomeStatus {

    private long id;
    private String category;
    private String date;

    public OutcomeStatus() {
    }

    public OutcomeStatus(String category, String date) {
        this.category = category;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
