package by.koshko.crimes.api;

import by.koshko.crimes.dao.impl.CrimeVolumeDao;
import by.koshko.crimes.service.QueryService;
import by.koshko.crimes.service.exception.ApplicationException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component("2")
public class QueryTwo implements ApplicationApiModule {

    private CrimeVolumeDao dao;

    @Autowired
    public QueryTwo(CrimeVolumeDao dao) {
        this.dao = dao;
    }

    @Override
    public void run(Properties parameters) {
        try {
            parameters.put("name", "month-to-month-crime-volume-comparison");
            parameters.put("header", "category,month,previous month count,current month count,delta,basic growth rate");
            new QueryService<>(dao).analyze(parameters);
        } catch (ApplicationException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage());
        }
    }
}
