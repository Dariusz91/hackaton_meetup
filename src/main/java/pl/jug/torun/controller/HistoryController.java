package pl.jug.torun.controller;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jug.torun.service.PrizeHistoryService;

import java.util.List;
import java.util.Map;

@RestController
public class HistoryController {

    private static Logger log = LoggerFactory.getLogger(HistoryController.class);

    @Autowired
    private PrizeHistoryService prizeHistoryService;

    @RequestMapping(value = "/history", produces = "application/json;charset=UTF-8")
    public String getHistoryMap() {
        Gson gson = new Gson();

        String json = gson.toJson(getMap());
        log.debug("[HistoryController] {}", json);
        return json;
    }

    private Map<String, List<String>> getMap() {
        return prizeHistoryService.createHistoryMap();
    }

}
