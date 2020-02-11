package by.koshko.crimes.entity;

public class OutcomeObject {

    private long id;
    private String identity;
    private String name;

    public OutcomeObject(String identity, String name) {
        this.identity = identity;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdentity() {
        return identity;
    }

    public String getName() {
        return name;
    }
}
