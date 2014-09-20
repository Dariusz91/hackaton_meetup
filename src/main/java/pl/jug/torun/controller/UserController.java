package pl.jug.torun.controller;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.jug.torun.domain.Participant;
import pl.jug.torun.service.ParticipantCreationService;
import pl.jug.torun.service.ParticipantDownloadService;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private ParticipantDownloadService participantDownloadService;

    @Autowired
    private ParticipantCreationService participantCreationService;

    @RequestMapping(value = "/users", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getUsers(@RequestParam Map<String, String> params) {

        if (!params.containsKey("appKey") || !params.containsKey("eventId")) {
            return "{\"error\":\"error\"}";
        }

        String appKey = params.get("appKey");
        String eventId = params.get("eventId");
        List<Participant> participants = participantDownloadService.downloadParticipants(appKey, eventId, "name");

        participants = participantCreationService.createParticipantsIfNotExists(participants);

        return convertToJson(participants);
    }

    private String convertToJson(List<Participant> participants) {
        Gson gson = new Gson();

        JsonElement participantsJsonArray = gson.toJsonTree(participants);

        JsonObject result = new JsonObject();
        result.add("participants", participantsJsonArray);

        return result.toString();
    }

}
