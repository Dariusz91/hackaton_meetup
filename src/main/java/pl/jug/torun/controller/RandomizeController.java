package pl.jug.torun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.jug.torun.domain.Draw;
import pl.jug.torun.domain.Participant;
import pl.jug.torun.domain.PrizeDefinition;
import pl.jug.torun.repository.DrawRepository;
import pl.jug.torun.repository.ParticipantRepository;
import pl.jug.torun.repository.PrizeDefinitionRepository;
import pl.jug.torun.service.PrizeAcceptanceService;
import pl.jug.torun.service.RandomizeService;

import java.util.Map;

@RestController
public class RandomizeController {

    @Autowired
    private RandomizeService randomizeService;

    @Autowired
    private PrizeAcceptanceService prizeAcceptanceService;

    @Autowired
    private PrizeDefinitionRepository prizeDefinitionRepository;

    @Autowired
    private DrawRepository drawRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @RequestMapping(value = "/randomize/get_participant", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String randomizeParticipant(@RequestParam Map<String, String> params) {

        if (!params.containsKey("prize_id") || !params.containsKey("draw_id")) {
            return "{\"error\":\"Brakuje draw_id lub prize_id\"}";
        }

        Long prizeId = Long.valueOf(params.get("prize_id"));
        Long drawId = Long.valueOf(params.get("draw_id"));

        PrizeDefinition prize = prizeDefinitionRepository.findOne(prizeId);
        Draw draw = drawRepository.findOne(drawId);

        if (prize == null || draw == null) {
            return "{\"error\":\"Nie znaleziono losowania lub nagrody\"}";
        }

        Participant participant = randomizeService.randomParticipant(draw, prize);

        return getParticipantIdInJsonForm(participant);
    }

    private String getParticipantIdInJsonForm(Participant participant) {
        return "{\"participant_id\":" + participant.getMemberId() + "}";
    }

    @RequestMapping(value = "/randomize/accept", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String acceptAward(@RequestParam Map<String, String> params) {

        if (!params.containsKey("prize_id") || !params.containsKey("draw_id") || !params.containsKey("participant_id")
                || !params.containsKey("accepted")) {
            return "{\"error\":\"Brakuje draw_id, participant_id, accepted lub prize_id\"}";
        }

        Long prizeId = Long.valueOf(params.get("prize_id"));
        Long drawId = Long.valueOf(params.get("draw_id"));
        String participantId = String.valueOf(params.get("participant_id"));
        boolean accepted = Boolean.valueOf(params.get("accepted"));

        PrizeDefinition prize = prizeDefinitionRepository.findOne(prizeId);
        Draw draw = drawRepository.findOne(drawId);
        Participant participant = participantRepository.findByMemberId(participantId);

        if (prize == null || draw == null || participant == null) {
            return "{\"error\":\"Nie znaleziono losowania, uczestnika lub nagrody\"}";
        }

        if (accepted) {
            prizeAcceptanceService.acceptParticipant(participant, draw, prize);
        } else {
            prizeAcceptanceService.discardParticipant(participant, draw);
        }

        return "{\"status\": \"success\"}";
    }
}
