package by.koshko.crimes.entity;

public class FailedRequest<T> {

    private T point;
    private String date;

    public FailedRequest(T point, String date) {
        this.date = date;
        this.point = point;
    }

    public String getDate() {
        return date;
    }

    public T getPoint() {
        return point;
    }
}
