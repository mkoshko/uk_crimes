package by.koshko.crimes.service;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationExecutor {

    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);

    public void execute(String file, String api, String dateFrom, String dateTo) throws ApplicationException {
        ExecutorServiceBuilder builder = (ExecutorServiceBuilder) context.getBean(api);
        builder.build().execute(file, dateFrom, dateTo);
    }
}
