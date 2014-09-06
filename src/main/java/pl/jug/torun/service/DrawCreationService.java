package pl.jug.torun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pl.jug.torun.domain.Draw;
import pl.jug.torun.domain.Participant;
import pl.jug.torun.domain.PrizeDefinition;
import pl.jug.torun.repository.DrawRepository;

import java.util.List;

public class DrawCreationService {

    @Autowired
    private DrawRepository drawRepository;

    @Transactional
    public void createDraw(String eventId, List<PrizeDefinition> prizes, List<Participant> participants) {
        Draw draw = new Draw();

        draw.setEventId(eventId);
        draw.setRemainingPrizes(prizes);
        draw.setRemainingParticipants(participants);

        drawRepository.save(draw);
    }
}
