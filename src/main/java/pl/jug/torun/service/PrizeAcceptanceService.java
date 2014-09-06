package pl.jug.torun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jug.torun.domain.Draw;
import pl.jug.torun.domain.Participant;
import pl.jug.torun.domain.PrizeDefinition;
import pl.jug.torun.domain.ReceivedPrize;
import pl.jug.torun.repository.DrawRepository;
import pl.jug.torun.repository.ReceivedPrizeRepository;

@Service
@Transactional
public class PrizeAcceptanceService {

    @Autowired
    private DrawRepository drawRepository;

    @Autowired
    private ReceivedPrizeRepository receivedPrizeRepository;

    public void acceptParticipant(Participant participant, Draw draw) {
        draw.getRemainingParticipants().remove(participant);
        PrizeDefinition prize = draw.getRemainingPrizes().get(0);
        draw.getRemainingPrizes().remove(prize);

        createReceivedPrize(participant, prize, draw);

        drawRepository.save(draw);
    }

    private void createReceivedPrize(Participant participant, PrizeDefinition prize, Draw draw) {
        ReceivedPrize receivedPrize = new ReceivedPrize();
        receivedPrize.setDraw(draw);
        receivedPrize.setParticipant(participant);
        receivedPrize.setPrizeDefinition(prize);

        receivedPrizeRepository.save(receivedPrize);
    }

    public void discardParticipant(Participant participant, Draw draw) {
        draw.getRemainingParticipants().remove(participant);

        drawRepository.save(draw);
    }
}
