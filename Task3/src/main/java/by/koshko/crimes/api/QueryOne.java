package by.koshko.crimes.api;

import by.koshko.crimes.dao.impl.MostDangerousStreetDao;
import by.koshko.crimes.service.QueryService;
import by.koshko.crimes.service.exception.ApplicationException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component("1")
public class QueryOne implements ApplicationApiModule {

    private MostDangerousStreetDao dao;

    @Autowired
    public QueryOne(MostDangerousStreetDao dao) {
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
