package by.koshko.crimes.exec;

import by.koshko.crimes.service.ApplicationExecutor;
import by.koshko.crimes.service.CommandLineParameters;
import by.koshko.crimes.service.ApplicationException;

public class Application {

    public static void main(String[] args) {
        CommandLineParameters cmd = new CommandLineParameters().build(args);
        try {
            new ApplicationExecutor().execute(cmd.getProperties());
        } catch (ApplicationException e) {
            System.err.println(e.getMessage());
        }
    }
}


