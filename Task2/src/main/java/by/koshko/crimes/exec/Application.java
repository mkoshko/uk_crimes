package by.koshko.crimes.exec;

import by.koshko.crimes.service.ApplicationExecutor;
import by.koshko.crimes.service.CommandLineParameters;
import by.koshko.crimes.service.ExecutionException;

public class Application {

    public static void main(String[] args) {
        CommandLineParameters cmd = new CommandLineParameters().build(args);
        try {
            new ApplicationExecutor().execute(
                    cmd.getValue(CommandLineParameters.FILE_OPTION),
                    cmd.getValue(CommandLineParameters.API_OPTION),
                    cmd.getValue(CommandLineParameters.FROM_OPTION),
                    cmd.getValue(CommandLineParameters.TO_OPTION)
            );
        } catch (ExecutionException e) {
            System.err.println(e.getMessage());
            cmd.printHelp();
        }
    }
}


