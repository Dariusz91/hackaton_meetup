package pl.jug.torun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jug.torun.domain.Draw;
import pl.jug.torun.domain.Participant;
import pl.jug.torun.repository.DrawRepository;

@Service
@Transactional
public class PrizeAcceptanceService {

    @Autowired
    private DrawRepository drawRepository;

    public void acceptParticipant(Participant participant, Draw draw) {
        draw.getRemainingParticipants().remove(participant);
        draw.getRemainingPrizes().remove(draw.getRemainingPrizes().get(0));

        drawRepository.save(draw);
    }

    public void discardParticipant(Participant participant, Draw draw) {
        draw.getRemainingParticipants().remove(participant);

        drawRepository.save(draw);
    }
}
