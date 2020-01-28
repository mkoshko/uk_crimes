package by.koshko.crimes.service;

import by.koshko.crimes.dao.CsvFileReader;
import by.koshko.crimes.entity.Point;
import by.koshko.crimes.service.mapper.PointMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Service
@Qualifier("street-level-crimes")
public class StreetCrimesExecutorService implements ExecutorService {

    private Logger logger = LoggerFactory.getLogger(StreetCrimesExecutorService.class);
    private PointMapper pointMapper;
    private StreetCrimesHttpRequestService requestService;
    private StreetCrimesJsonArrayHandler jsonArrayHandler;

    @Autowired
    public StreetCrimesExecutorService(PointMapper pointMapper,
                                       StreetCrimesHttpRequestService requestService,
                                       StreetCrimesJsonArrayHandler jsonArrayHandler) {
        this.pointMapper = pointMapper;
        this.requestService = requestService;
        this.jsonArrayHandler = jsonArrayHandler;
    }

    @Override
    public void execute(String file, String dateFrom, String dateTo) {
        try {
            List<Point> points = readPointsFromFile(file);
            points.parallelStream().forEach(point -> {
                try {
                    String jsonResponse = requestService.sendRequest(point);
                    jsonArrayHandler.process(jsonResponse);
                } catch (ServiceException e) {
                    logger.error(e.getMessage());
                }
            });
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private List<Point> readPointsFromFile(String file) throws IOException {
        Stream<String> coordinates = CsvFileReader.getLinesAsStream(file);
        return pointMapper.map(coordinates);
    }
}
