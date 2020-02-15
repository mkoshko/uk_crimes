package by.koshko.crimes.service;


import by.koshko.crimes.dao.QueryDao;
import by.koshko.crimes.service.exception.ApplicationException;
import by.koshko.crimes.util.CommandLineParameters;
import by.koshko.crimes.util.ReportWriter;
import by.koshko.crimes.util.SqlScriptReader;

import java.util.List;
import java.util.Properties;

public class QueryService<T> {

    private QueryDao<T> queryDao;

    public QueryService(QueryDao<T> queryDao) {
        this.queryDao = queryDao;
    }

    public void analyze(Properties parameters) throws ApplicationException {
        int queryIndex = Integer.parseInt(parameters.getProperty(CommandLineParameters.API_OPTION));
        int printRows = Integer.parseInt(parameters.getProperty(CommandLineParameters.PRINT_ROWS));
        String sqlFolder = parameters.getProperty(CommandLineParameters.FILE_OPTION);
        String sql = SqlScriptReader.readSql(sqlFolder + queryIndex + ".sql");
        List<T> data = queryDao.get(sql);
        printRows(data, printRows);
        ReportWriter.writeReport(data);
    }

    private void printRows(List<T> data, int rows) {
        data.stream().limit(rows).forEach(System.out::println);
    }
}
