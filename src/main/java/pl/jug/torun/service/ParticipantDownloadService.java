package pl.jug.torun.service;

import com.google.gson.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import pl.jug.torun.domain.Participant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParticipantDownloadService {

    private static final String RESULTS_NAME = "results";
    private static final String MEMBER_NAME = "member";

    private static Gson gson = new Gson();

    private RestTemplate restTemplate;

    public ParticipantDownloadService() {
        this.restTemplate = new RestTemplate();
    }

    public String get(String key, String eventId, String order) {
        Map<String, String> vars = new HashMap<>();

        vars.put("key", key);
        vars.put("event_id", eventId);
        vars.put("order", order);

        return restTemplate.getForObject("http://api.meetup.com/2/rsvps?key={key}&event_id={event_id}&order=name",
                String.class, vars);
    }

    @Transactional
    public List<Participant> downloadParticipants(String key, String eventId, String name) {
        String response = get(key, eventId, name);
        return responseToParticipantList(response);
    }

    public List<Participant> responseToParticipantList(String json) {
        JsonArray resultsJsonArray = getResults(json);
        List<Participant> participants = new ArrayList<>();

        for (int i = 0; i < resultsJsonArray.size(); i++) {
            Participant participant = getParticipantFromResult(resultsJsonArray.get(i));
            participants.add(participant);
        }

        return participants;
    }

    private JsonArray getResults(String json) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(json).getAsJsonObject();
        JsonElement jsonElement = jsonObject.get(RESULTS_NAME);

        return jsonElement.getAsJsonArray();
    }

    private Participant getParticipantFromResult(JsonElement json) {
        String member = json.getAsJsonObject().get(MEMBER_NAME).toString();
        return gson.fromJson(member, Participant.class);
    }
}
