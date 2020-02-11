package by.koshko.crimes.exec;

import by.koshko.crimes.config.RootConfig;
import by.koshko.crimes.util.CommandLineParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
        CommandLineParameters cmd = new CommandLineParameters().build(args);
        String requiredAPI = cmd.getProperties().getProperty(CommandLineParameters.API_OPTION);
        ApplicationAPIModule module = (ApplicationAPIModule) context.getBean(requiredAPI);
        long start = System.currentTimeMillis();
        try {
            module.run(cmd.getProperties());
        } catch (Throwable e) {
            logger.error(e.getMessage());
        }
        System.out.println("Elapsed time: " + (System.currentTimeMillis() - start) + " ms.");
    }
}


