package by.koshko.crimes.exec;

import by.koshko.crimes.api.ApplicationApiModule;
import by.koshko.crimes.config.RootConfig;
import by.koshko.crimes.service.exception.ApplicationException;
import by.koshko.crimes.util.CommandLineParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Application.class);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
        CommandLineParameters cmd = new CommandLineParameters().build(args);
        String requiredAPI = cmd.getProperties().getProperty(CommandLineParameters.API_OPTION);
        ApplicationApiModule apiModule = (ApplicationApiModule) context.getBean(requiredAPI);
        try {
            long start = System.currentTimeMillis();
            apiModule.run(cmd.getProperties());
            logger.info("Elapsed time: " + (System.currentTimeMillis() - start) + " ms.");
        } catch (ApplicationException e) {
            logger.error(e.getMessage());
        }
    }
}


