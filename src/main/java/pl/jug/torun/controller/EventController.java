package pl.jug.torun.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import pl.jug.torun.domain.Event;
import pl.jug.torun.service.EventCreationService;
import pl.jug.torun.service.EventDownloadService;

import java.util.List;
import java.util.Map;

@RestController
public class EventController {

    private static final Gson gson = new Gson();

    @Autowired
    private EventDownloadService eventDownloadService;

    @Autowired
    private EventCreationService eventCreationService;

    @Value("${group.name:Torun-JUG}")
    private String groupName;

    @RequestMapping(value = "/events", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public String downloadAllGroupEvents(@RequestParam Map<String, String> params) {

        if (!params.containsKey("appKey")) {
            return "{\"error\":\"Nie podano appKey\"}";
        }

        String appKey = params.get("appKey");
        List<Event> events = eventDownloadService.downloadEvents(appKey, groupName);
        eventCreationService.createOnlyNewEvents(events);

        return createEventsJson(events);
    }

    private String createEventsJson(List<Event> events) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("events", gson.toJsonTree(events));
        return jsonObject.toString();
    }

}
