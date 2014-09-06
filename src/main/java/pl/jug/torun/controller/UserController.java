package pl.jug.torun.controller;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.jug.torun.domain.Participant;
import pl.jug.torun.service.ParticipantCreationService;
import pl.jug.torun.service.ParticipantDownloadService;
import pl.jug.torun.utils.HeaderProvider;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private ParticipantDownloadService participantDownloadService;

    @Autowired
    private ParticipantCreationService participantCreationService;

    @RequestMapping("/users")
    @ResponseBody
    public HttpEntity<String> getUsers(@RequestParam Map<String, String> params) {

        HttpHeaders headers = HeaderProvider.createHeaders();

        if (!params.containsKey("appKey") || !params.containsKey("eventId")) {
            return new HttpEntity<>("{\"error\":\"error\"}", headers);
        }

        String appKey = params.get("appKey");
        String eventId = params.get("eventId");
        List<Participant> participants = participantDownloadService.downloadParticipants(appKey, eventId, "name");

        participants = participantCreationService.createParticipantsIfNotExists(participants);

        return new HttpEntity<>(convertToJson(participants), headers);
    }

    private String convertToJson(List<Participant> participants) {
        Gson gson = new Gson();

        JsonElement participantsJsonArray = gson.toJsonTree(participants);

        JsonObject result = new JsonObject();
        result.add("participants", participantsJsonArray);

        return result.toString();
    }

}
