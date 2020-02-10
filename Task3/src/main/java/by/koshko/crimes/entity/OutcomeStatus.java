package by.koshko.crimes.entity;

public class OutcomeStatus {

    private long id;
    private long categoryNameId;
    private String category;
    private String date;

    public OutcomeStatus(String category, String date) {
        this.category = category;
        this.date = date;
    }

    public long getCategoryNameId() {
        return categoryNameId;
    }

    public void setCategoryNameId(long categoryNameId) {
        this.categoryNameId = categoryNameId;
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
