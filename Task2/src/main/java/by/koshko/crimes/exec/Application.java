package by.koshko.crimes.exec;

import by.koshko.crimes.service.ApplicationExecutor;

public class Application {

    public static void main(String[] args) {
        new ApplicationExecutor().execute("data/LondonStations.csv", "street-level-crimes", null, null);
    }

}
