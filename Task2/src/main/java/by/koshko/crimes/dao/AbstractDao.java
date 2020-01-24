package by.koshko.crimes.dao;

import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractDao {

    protected FluentJdbc fluentJdbc;

    @Autowired
    public void setFluentJdbc(FluentJdbc fluentJdbc) {
        this.fluentJdbc = fluentJdbc;
    }

}
