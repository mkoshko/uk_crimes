package by.koshko.crimes.api;

import by.koshko.crimes.dao.impl.MostProbableStopSearchDao;
import by.koshko.crimes.service.QueryService;
import by.koshko.crimes.service.exception.ApplicationException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component("5")
public class QueryFive implements ApplicationApiModule {

    private MostProbableStopSearchDao dao;

    @Autowired
    public QueryFive(MostProbableStopSearchDao dao) {
        this.dao = dao;
    }

    @Override
    public void run(Properties parameters) {
        try {
            new QueryService<>(dao).analyze(parameters);
        } catch (ApplicationException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage());
        }
    }
}
