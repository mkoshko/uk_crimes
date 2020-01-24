package by.koshko.crimes.entity;

public class Location {

    private long id;
    private Street street;
    private double latitude;
    private double longitude;

    public Location() {
    }

    public Location(long id, Street street, double latitude, double longitude) {
        this.id = id;
        this.street = street;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
