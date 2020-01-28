package by.koshko.crimes.service;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationExecutor {

    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);

    public void execute(String file, String api, String dateFrom, String dateTo) {
        ExecutorServiceBuilder builder = (ExecutorServiceBuilder) context.getBean(api);
        ExecutorService executorService = builder.build();
        executorService.execute(file, dateFrom, dateTo);
    }
}
