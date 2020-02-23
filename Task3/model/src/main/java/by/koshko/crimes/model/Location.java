package by.koshko.crimes.model;

public class Location {

    private Street street;
    private double latitude;
    private double longitude;

    public Location() {
    }

    public Location(Street street, double latitude, double longitude) {
        this.street = street;
        this.latitude = latitude;
        this.longitude = longitude;
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
