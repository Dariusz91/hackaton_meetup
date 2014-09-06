package pl.jug.torun.builders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.jug.torun.domain.Draw;
import pl.jug.torun.domain.Participant;
import pl.jug.torun.domain.PrizeDefinition;
import pl.jug.torun.domain.ReceivedPrize;
import pl.jug.torun.repository.DrawRepository;
import pl.jug.torun.repository.ParticipantRepository;
import pl.jug.torun.repository.PrizeDefinitionRepository;
import pl.jug.torun.repository.ReceivedPrizeRepository;

@Component
public class ComponentBuilders {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private PrizeDefinitionRepository prizeDefinitionRepository;

    @Autowired
    private ReceivedPrizeRepository receivedPrizeRepository;

    @Autowired
    private DrawRepository drawRepository;

    public Participant createParticipant(String memberId) {
        Participant participant = new Participant();
        participant.setMemberId(memberId);
        participantRepository.save(participant);

        return participant;
    }

    public PrizeDefinition createPrizeDefinition(String name) {
        PrizeDefinition prizeDefinition = new PrizeDefinition();
        prizeDefinition.setName(name);

        prizeDefinitionRepository.save(prizeDefinition);
        return prizeDefinition;
    }

    public ReceivedPrize createReceivedPrize(Draw draw, Participant participant, PrizeDefinition prizeDefinition) {
        ReceivedPrize receivedPrize = new ReceivedPrize();
        receivedPrize.setDraw(draw);
        receivedPrize.setParticipant(participant);
        receivedPrize.setPrizeDefinition(prizeDefinition);

        receivedPrizeRepository.save(receivedPrize);
        return receivedPrize;
    }

    public Draw createDraw(String eventId) {
        Draw draw = new Draw();
        draw.setEventId(eventId);

        drawRepository.save(draw);
        return draw;
    }

}
