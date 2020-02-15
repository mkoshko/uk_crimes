package by.koshko.crimes.api;

import by.koshko.crimes.dao.impl.CrimesWithSpecOutcomeDao;
import by.koshko.crimes.service.QueryService;
import by.koshko.crimes.service.exception.ApplicationException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component("3")
public class QueryThree implements ApplicationApiModule {

    private CrimesWithSpecOutcomeDao dao;

    @Autowired
    public QueryThree(CrimesWithSpecOutcomeDao dao) {
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
