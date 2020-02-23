package by.koshko.crimes.model;

public class Force {

    private String id;

    public Force(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + ".";
    }
}
