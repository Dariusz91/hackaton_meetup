package pl.jug.torun.builders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.jug.torun.domain.*;
import pl.jug.torun.repository.*;

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

    @Autowired
    private EventRepository eventRepository;

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

    public Draw createDraw(Event event) {
        Draw draw = new Draw();
        draw.setEvent(event);

        drawRepository.save(draw);
        return draw;
    }

    public Event createEvent(String eventId, String name) {
        Event event = new Event();
        event.setEventId(eventId);
        event.setName(name);

        eventRepository.save(event);
        return event;
    }

}
