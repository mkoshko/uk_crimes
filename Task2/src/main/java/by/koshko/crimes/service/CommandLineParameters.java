package by.koshko.crimes.service;

import org.apache.commons.cli.*;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class CommandLineParameters {

    public static final String FILE_OPTION = "file";
    public static final String API_OPTION = "api";
    public static final String FROM_OPTION = "from";
    public static final String TO_OPTION = "to";

    private Options options = new Options();
    private Options helpOptions = new Options();

    public void build() {
        options.addOption(createDefaultOption());
        createHelpOptions().forEach(helpOptions::addOption);
    }

    private Option createDefaultOption() {
        return Option.builder()
                .longOpt("D")
                .argName("property=value")
                .hasArg()
                .required()
                .valueSeparator()
                .numberOfArgs(2)
                .desc("use value for given properties")
                .build();
    }

    private List<Option> createHelpOptions() {
        String argName = "property=value";
        Option help = new Option("h", "help", false, "prints this help message.");
        help.setArgName(argName);
        Option api = new Option("Dapi", null, true, "api method to use.(REQUIRED)");
        api.setArgName(argName);
        Option file = new Option("Dfile", null, true, "path to a file.(REQUIRED)");
        file.setArgName(argName);
        Option dateFrom = new Option("Dfrom", null, true, "date from search will start YYYY-mm.");
        dateFrom.setArgName(argName);
        Option dateTo = new Option("Dto", null, true, "search date to YYYY-mm.");
        dateTo.setArgName(argName);
        return Arrays.asList(help, api, file, dateFrom, dateTo);
    }

    public Properties parse(String... args) {
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("h")) {
                printHelp();
                System.exit(0);
            }
            return cmd.getOptionProperties("D");
        } catch (ParseException e) {
            printHelp();
            System.exit(0);
        }
        return new Properties();
    }

    public void printHelp() {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("crime [OPTION]...", helpOptions);
    }
}
