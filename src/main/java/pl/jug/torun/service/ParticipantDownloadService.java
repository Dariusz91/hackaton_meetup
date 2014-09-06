package pl.jug.torun.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jug.torun.domain.Participant;

import java.util.List;

@Service
public class ParticipantDownloadService {

    @Transactional
    public List<Participant> downloadParticipants() {
        return null;
    }

}
