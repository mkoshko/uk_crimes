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
            parameters.put("name", "most-probable-Stop-and-Search-snapshot-on-street-level");
            parameters.put("header", "street id;street name;age range;gender;ethnicity;object of search;outcome");
            new QueryService<>(dao).analyze(parameters);
        } catch (ApplicationException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage());
        }
    }
}
