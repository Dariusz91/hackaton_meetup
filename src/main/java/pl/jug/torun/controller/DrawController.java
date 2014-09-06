package pl.jug.torun.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jug.torun.domain.Participant;
import pl.jug.torun.domain.PrizeDefinition;
import pl.jug.torun.service.DrawCreationService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DrawController {

    private static Gson gson = new Gson();

    @Autowired
    private DrawCreationService drawCreationService;

    @RequestMapping("/draw/start")
    public int getPrizesAmount(@RequestBody String json) {

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(json).getAsJsonObject();

        String eventId = jsonObject.get("event_id").getAsString();
        JsonArray prizesJsonArray = jsonObject.get("prizes").getAsJsonArray();
        JsonArray participantsJsonArray = jsonObject.get("participants").getAsJsonArray();

        List<PrizeDefinition> prizes = jsonArrayToPrizes(prizesJsonArray);
        List<Participant> participants = jsonArrayToParticipants(participantsJsonArray);

        drawCreationService.createDraw(eventId, prizes, participants);

        return 200;
    }

    private List<Participant> jsonArrayToParticipants(JsonArray jsonArray) {
        List<Participant> participants = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            participants.add(gson.fromJson(jsonArray.get(i), Participant.class));
        }

        return participants;
    }

    private List<PrizeDefinition> jsonArrayToPrizes(JsonArray jsonArray) {
        List<PrizeDefinition> prizes = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            prizes.add(gson.fromJson(jsonArray.get(i), PrizeDefinition.class));
        }

        return prizes;
    }
}
