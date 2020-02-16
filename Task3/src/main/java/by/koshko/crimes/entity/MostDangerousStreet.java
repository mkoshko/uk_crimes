package by.koshko.crimes.entity;

public class MostDangerousStreet {

    private long id;
    private String name;
    private String period;
    private int crimesNumber;

    public MostDangerousStreet(long id, String name, String period, int crimesNumber) {
        this.id = id;
        this.name = name;
        this.period = period;
        this.crimesNumber = crimesNumber;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPeriod() {
        return period;
    }

    public int getCrimesNumber() {
        return crimesNumber;
    }

    @Override
    public String toString() {
        return String.format("%d;%s;%s,%d", id, name, period, crimesNumber);
    }
}
