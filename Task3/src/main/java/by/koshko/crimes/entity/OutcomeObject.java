package by.koshko.crimes.entity;

public class OutcomeObject {

    private long id;
    private String identity;
    private String name;

    public OutcomeObject(long id, String identity, String name) {
        this.id = id;
        this.identity = identity;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getIdentity() {
        return identity;
    }

    public String getName() {
        return name;
    }
}