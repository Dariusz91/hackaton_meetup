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

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
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
    public String getUsers(@RequestParam Map<String, String> params, HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        if (!params.containsKey("appKey") || !params.containsKey("eventId")) {
            return "{\"error\":\"error\"}";
        }

        //TODO remove mock
        //FIXME encoding
//        List<Participant> participants = participantDownloadService.downloadParticipants();
        Participant p1 = new Participant();
        p1.setName("Towarzysz Bęgowski");
        p1.setMemberId("1");
        Participant p2 = new Participant();
        p2.setName("Towarzysz Lenin");
        p2.setMemberId("2");
        List<Participant> participants = Arrays.asList(p1, p2);
        participantCreationService.createParticipantsIfNotExists(participants);

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
