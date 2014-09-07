package pl.jug.torun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
import pl.jug.torun.utils.HeaderProvider;

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

    @RequestMapping(value = "/randomize/get_participant")
    @ResponseBody
    public HttpEntity<String> randomizeParticipant(@RequestParam Map<String, String> params) {
        HttpHeaders headers = HeaderProvider.createHeaders();

        if (!params.containsKey("prize_id") || !params.containsKey("draw_id")) {
            return new HttpEntity<>("{\"error\":\"Brakuje draw_id lub prize_id\"}", headers);
        }

        Long prizeId = Long.valueOf(params.get("prize_id"));
        Long drawId = Long.valueOf(params.get("draw_id"));

        PrizeDefinition prize = prizeDefinitionRepository.findOne(prizeId);
        Draw draw = drawRepository.findOne(drawId);

        if (prize == null || draw == null) {
            return new HttpEntity<>("{\"error\":\"Nie znaleziono losowania lub nagrody\"}", headers);
        }

        Participant participant = randomizeService.randomParticipant(draw, prize);

        return getParticipantIdInJsonForm(participant, headers);
    }

    private HttpEntity<String> getParticipantIdInJsonForm(Participant participant, HttpHeaders headers) {
        return new HttpEntity<>("{\"participant_id\":" + participant.getMemberId() + "}", headers);
    }

    @RequestMapping(value = "/randomize/accept")
    @ResponseBody
    public HttpEntity<String> acceptAward(@RequestParam Map<String, String> params) {
        HttpHeaders headers = HeaderProvider.createHeaders();

        if (!params.containsKey("prize_id") || !params.containsKey("draw_id") || !params.containsKey("participant_id")
                || !params.containsKey("accepted")) {
            return new HttpEntity<>("{\"error\":\"Brakuje draw_id, participant_id, accepted lub prize_id\"}", headers);
        }

        Long prizeId = Long.valueOf(params.get("prize_id"));
        Long drawId = Long.valueOf(params.get("draw_id"));
        String participantId = String.valueOf(params.get("participant_id"));
        boolean accepted = Boolean.valueOf(params.get("accepted"));

        PrizeDefinition prize = prizeDefinitionRepository.findOne(prizeId);
        Draw draw = drawRepository.findOne(drawId);
        Participant participant = participantRepository.findByMemberId(participantId);

        if (prize == null || draw == null || participant == null) {
            return new HttpEntity<>("{\"error\":\"Nie znaleziono losowania, uczestnika lub nagrody\"}", headers);
        }

        if (accepted) {
            prizeAcceptanceService.acceptParticipant(participant, draw, prize);
        } else {
            prizeAcceptanceService.discardParticipant(participant, draw);
        }

        return new HttpEntity<>("{\"status\": \"success\"}", headers);
    }
}
