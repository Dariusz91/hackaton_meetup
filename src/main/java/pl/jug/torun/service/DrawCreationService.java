package pl.jug.torun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jug.torun.domain.Draw;
import pl.jug.torun.domain.Event;
import pl.jug.torun.domain.Participant;
import pl.jug.torun.domain.PrizeDefinition;
import pl.jug.torun.repository.DrawRepository;

import java.util.List;

@Service
public class DrawCreationService {

    @Autowired
    private DrawRepository drawRepository;

    @Transactional
    public Draw createDraw(Event event, List<PrizeDefinition> prizes, List<Participant> participants) {
        Draw draw = new Draw();

        draw.setEvent(event);
        draw.setRemainingPrizes(prizes);
        draw.setRemainingParticipants(participants);

        drawRepository.save(draw);

        return draw;
    }
}
