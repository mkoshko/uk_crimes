package by.koshko.crimes.api;

import by.koshko.crimes.dao.impl.StopSearchCorrelationWithCrimeDao;
import by.koshko.crimes.service.QueryService;
import by.koshko.crimes.service.exception.ApplicationException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component("6")
public class QuerySix implements ApplicationApiModule {

    private StopSearchCorrelationWithCrimeDao dao;

    @Autowired
    public QuerySix(StopSearchCorrelationWithCrimeDao dao) {
        this.dao = dao;
    }

    @Override
    public void run(Properties parameters) {
        try {
            parameters.put("name", "stop-and-Search-correlation-with-crimes");
            parameters.put("header", "street id,street name,month,drugs crimes count,drugs stop-and-search count,"
                    + "weapons crimes count,weapons stop-and-search count,theft crimes count,"
                    + "theft stop-and-search count");
            new QueryService<>(dao).analyze(parameters);
        } catch (ApplicationException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage());
        }
    }
}