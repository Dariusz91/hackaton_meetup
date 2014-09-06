package pl.jug.torun.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import pl.jug.torun.domain.Participant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParticipantDownloadService {

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
    public List<Participant> downloadParticipants(String key, String eventId) {
        String response = get(key, eventId, "name");

        return null;
    }
}
