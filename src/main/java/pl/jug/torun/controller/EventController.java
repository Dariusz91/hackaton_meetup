package pl.jug.torun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.jug.torun.domain.Event;
import pl.jug.torun.service.EventCreationService;
import pl.jug.torun.service.EventDownloadService;

import java.util.List;
import java.util.Map;

@RestController
public class EventController {

    @Autowired
    private EventDownloadService eventDownloadService;

    @Autowired
    private EventCreationService eventCreationService;

    private String groupName = "Torun-JUG";

    @RequestMapping(value = "/events", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String downloadAllGroupEvents(@RequestParam Map<String, String> params) {

        if (!params.containsKey("appKey")) {
            return "{\"error\":\"Nie podano appKey\"}";
        }

        String appKey = params.get("appKey");
        List<Event> events = eventDownloadService.downloadEvents(appKey, groupName);
        eventCreationService.createOnlyNewEvents(events);

        return "{\"status\":\"success\"}";
    }

}
