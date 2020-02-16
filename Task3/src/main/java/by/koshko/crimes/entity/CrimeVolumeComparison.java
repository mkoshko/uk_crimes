package by.koshko.crimes.entity;

public class CrimeVolumeComparison {

    private String category;
    private String month;
    private Integer previousMonth;
    private Integer currentMonth;
    private Integer delta;
    private float growth;

    public CrimeVolumeComparison(String category,
                                 String month,
                                 Integer previousMonth,
                                 Integer currentMonth,
                                 Integer delta,
                                 float growth) {
        this.category = category;
        this.month = month;
        this.previousMonth = previousMonth;
        this.currentMonth = currentMonth;
        this.delta = delta;
        this.growth = growth;
    }

    public String getCategory() {
        return category;
    }

    public String getMonth() {
        return month;
    }

    public Integer getPreviousMonth() {
        return previousMonth;
    }

    public Integer getCurrentMonth() {
        return currentMonth;
    }

    public Integer getDelta() {
        return delta;
    }

    public float getGrowth() {
        return growth;
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%d;%d;%d;%.2f;", category, month, previousMonth, currentMonth, delta, growth);
    }
}
