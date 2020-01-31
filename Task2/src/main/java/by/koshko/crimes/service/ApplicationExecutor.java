package by.koshko.crimes.service;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Properties;

public class ApplicationExecutor {

    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);

    public void execute(Properties properties) throws ApplicationException {
        ExecutorServiceBuilder builder
                = (ExecutorServiceBuilder) context.getBean(properties.getProperty(CommandLineParameters.API_OPTION));
        builder.build().execute(
                properties.getProperty(CommandLineParameters.FILE_OPTION),
                properties.getProperty(CommandLineParameters.FROM_OPTION),
                properties.getProperty(CommandLineParameters.TO_OPTION));
    }
}
