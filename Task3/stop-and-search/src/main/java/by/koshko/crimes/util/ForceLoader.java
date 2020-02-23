package by.koshko.crimes.util;

import by.koshko.crimes.model.Force;
import by.koshko.crimes.service.exception.ServiceException;
import by.koshko.crimes.service.request.AvailableForcesHttpRequest;
import by.koshko.crimes.service.jsonutil.JsonArrayStreamSupport;
import by.koshko.crimes.service.mapper.ForceMapper;
import org.json.JSONArray;

import java.util.List;
import java.util.stream.Collectors;

public class ForceLoader {

    private String url = "https://data.police.uk/api/forces";
    private AvailableForcesHttpRequest requestService = new AvailableForcesHttpRequest(url);
    private ForceMapper forceMapper = new ForceMapper();

    public List<Force> getForces() throws ServiceException {
        String response = requestService.sendRequest(null);
        return JsonArrayStreamSupport.toStream(new JSONArray(response))
                .map(forceMapper::map)
                .collect(Collectors.toList());
    }
}
