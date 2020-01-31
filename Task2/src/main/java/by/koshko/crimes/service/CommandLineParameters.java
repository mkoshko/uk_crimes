package by.koshko.crimes.service;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class CommandLineParameters {

    public static final String FILE_OPTION = "file";
    public static final String API_OPTION = "api";
    public static final String FROM_OPTION = "from";
    public static final String TO_OPTION = "to";
    public static final String TARGET_OPTION = "target";

    private Options options = new Options();
    private Options helpOptions = new Options();
    private Properties properties;

    public CommandLineParameters build(String... args) {
        options.addOption(createDefaultOption());
        createHelpOptions().forEach(helpOptions::addOption);
        parse(args);
        return this;
    }

    public String getValue(String key) {
        return properties.getProperty(key);
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

    private void parse(String... args) {
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("h")) {
                printHelp();
                System.exit(0);
            }
            properties = cmd.getOptionProperties("D");
        } catch (ParseException e) {
            printHelp();
        }
    }

    public void printHelp() {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("crime [OPTION]...", helpOptions);
        System.out.println("Available API methods: ");
        System.out.println("1. street-level-crimes");
    }

    private List<Option> createHelpOptions() {
        String argName = "property=value";
        Option help = new Option("h", "help", false, "prints this help message.");
        help.setArgName(argName);
        options.addOption(help);
        Option api = new Option("Dapi", null, true, "api method to use.(REQUIRED)");
        api.setArgName(argName);
        Option file = new Option("Dfile", null, true, "path to a file.(REQUIRED)");
        file.setArgName(argName);
        Option dateFrom = new Option("Dfrom", null, true, "start date YYYY-mm.");
        dateFrom.setArgName(argName);
        Option dateTo = new Option("Dto", null, true, "end date YYYY-mm.");
        dateTo.setArgName(argName);
        Option target = new Option("Dtarget", null, true, "save location database or file.");
        target.setArgName(argName);
        return Arrays.asList(help, api, file, dateFrom, dateTo);
    }
}
