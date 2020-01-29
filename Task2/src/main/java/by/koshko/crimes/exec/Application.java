package by.koshko.crimes.exec;

import by.koshko.crimes.service.ApplicationExecutor;
import by.koshko.crimes.service.CommandLineParameters;

import java.util.Properties;

public class Application {

    public static void main(String[] args) {
        CommandLineParameters cmd = new CommandLineParameters();
        cmd.build();
        Properties properties = cmd.parse(args);
        if (isRequiredParametersProvided(properties, cmd)) {
            new ApplicationExecutor().execute(
                    properties.getProperty(CommandLineParameters.FILE_OPTION),
                    properties.getProperty(CommandLineParameters.API_OPTION),
                    properties.getProperty(CommandLineParameters.FROM_OPTION),
                    properties.getProperty(CommandLineParameters.TO_OPTION)
            );
        }

    }

    private static boolean isRequiredParametersProvided(Properties properties, CommandLineParameters cmd) {
        boolean isValid = true;
        String file = properties.getProperty(CommandLineParameters.FILE_OPTION);
        String api = properties.getProperty(CommandLineParameters.API_OPTION);
        if (file == null || file.isBlank()) {
            isValid = false;
            System.err.println("Path to file is not provided.");
        } else if (api == null || api.isBlank()) {
            isValid = false;
            System.err.println("Api method is not provided");
        }
        return isValid;
    }
}


