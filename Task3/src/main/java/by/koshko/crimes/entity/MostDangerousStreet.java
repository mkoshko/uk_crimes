package by.koshko.crimes.entity;

public class MostDangerousStreet {

    private long id;
    private String name;
    private int crimesNumber;

    public MostDangerousStreet(long id, String name, int crimesNumber) {
        this.id = id;
        this.name = name;
        this.crimesNumber = crimesNumber;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCrimesNumber() {
        return crimesNumber;
    }

    @Override
    public String toString() {
        return String.format("%d;%s;%d", id, name, crimesNumber);
    }
}
