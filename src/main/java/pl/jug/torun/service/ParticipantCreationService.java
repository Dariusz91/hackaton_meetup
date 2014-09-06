package pl.jug.torun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jug.torun.domain.Participant;
import pl.jug.torun.repository.ParticipantRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParticipantCreationService {

    @Autowired
    private ParticipantRepository participantRepository;

    public List<Participant> createParticipantsIfNotExists(List<Participant> participants) {

        List<Participant> results = new ArrayList<>();

        for (Participant participant : participants) {
            Participant p = getParticipantsOrCreateIfNotExist(participant);
            results.add(p);
        }

        return results;
    }

    private Participant getParticipantsOrCreateIfNotExist(Participant participant) {
        Participant fromDb = participantRepository.findByMemberId(participant.getMemberId());

        if (fromDb != null) {
            return fromDb;
        } else {
            participantRepository.save(participant);
            return participant;
        }
    }

}
