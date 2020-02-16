package by.koshko.crimes.entity;

public class CrimeWithSpecOutcome {

    private long id;
    private String name;
    private String category;
    private int crimesNumber;
    private float percentage;

    public CrimeWithSpecOutcome(long id, String name, String category, int crimesNumber, float percentage) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.crimesNumber = crimesNumber;
        this.percentage = percentage;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getCrimesNumber() {
        return crimesNumber;
    }

    public float getPercentage() {
        return percentage;
    }

    @Override
    public String toString() {
        return String.format("%d;%s;%s;%d;%.2f", id, name, category, crimesNumber, percentage);
    }
}
