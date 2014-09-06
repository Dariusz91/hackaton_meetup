package pl.jug.torun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jug.torun.domain.Participant;
import pl.jug.torun.repository.ParticipantRepository;

import java.util.List;
import java.util.stream.Stream;

@Service
public class ParticipantCreationService {

    @Autowired
    private ParticipantRepository participantRepository;

    public void createParticipantsIfNotExists(List<Participant> participants) {
        List<Participant> inDb = participantRepository.findAll();

        Stream<Participant> toCreate = participants.stream().filter(p -> !inDb.contains(p));

        createNonExisting(toCreate);
    }

    private void createNonExisting(Stream<Participant> toCreate) {
        toCreate.forEach(participantRepository::save);
    }

}
