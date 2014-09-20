package pl.jug.torun.service;

import com.google.gson.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import pl.jug.torun.domain.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventDownloadService {

    private static final String RESULTS_NAME = "results";
    private static final String EVENT_ID_NAME = "id";
    private static final String EVENT_NAME = "name";

    private static Gson gson = new Gson();

    private RestTemplate restTemplate;

    public EventDownloadService() {
        this.restTemplate = new RestTemplate();
    }

    public String get(String key, String groupUrlname) {
        Map<String, String> vars = new HashMap<>();

        vars.put("key", key);
        vars.put("group_urlname", groupUrlname);

        return restTemplate.getForObject("http://api.meetup.com/2/events?key={key}&group_urlname={group_urlname}",
                String.class, vars);
    }

    @Transactional
    public List<Event> downloadEvents(String key, String groupUrlname) {
        String response = get(key, groupUrlname);
        return responseToEventList(response);
    }

    public List<Event> responseToEventList(String json) {
        JsonArray resultsJsonArray = getResults(json);
        List<Event> events = new ArrayList<>();

        for (int i = 0; i < resultsJsonArray.size(); i++) {
            Event event = getEventFromResult(resultsJsonArray.get(i));
            events.add(event);
        }

        return events;
    }

    private JsonArray getResults(String json) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(json).getAsJsonObject();
        JsonElement jsonElement = jsonObject.get(RESULTS_NAME);

        return jsonElement.getAsJsonArray();
    }

    private Event getEventFromResult(JsonElement json) {
        String id = json.getAsJsonObject().get(EVENT_ID_NAME).getAsString();
        String name = json.getAsJsonObject().get(EVENT_NAME).getAsString();

        Event event = new Event();
        event.setEventId(id);
        event.setName(name);

        return event;
    }
}
