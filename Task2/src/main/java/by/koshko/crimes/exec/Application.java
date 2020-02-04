package by.koshko.crimes.exec;

import by.koshko.crimes.service.exception.ApplicationException;
import by.koshko.crimes.util.CommandLineParameters;
import by.koshko.crimes.service.ExecutorServiceBuilder;
import by.koshko.crimes.config.RootConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Properties;

public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
        CommandLineParameters cmd = new CommandLineParameters().build(args);
        Properties parameters = cmd.getProperties();
        long start = System.currentTimeMillis();
        try {
            ExecutorServiceBuilder builder
                    = (ExecutorServiceBuilder) context.getBean(parameters.getProperty(CommandLineParameters.API_OPTION));
            builder.build().execute(
                    parameters.getProperty(CommandLineParameters.FILE_OPTION),
                    parameters.getProperty(CommandLineParameters.FROM_OPTION),
                    parameters.getProperty(CommandLineParameters.TO_OPTION));
        } catch (ApplicationException e) {
            logger.error(e.getMessage());
        }
        System.out.println("Elapsed time: " + (System.currentTimeMillis() - start));
    }
}


