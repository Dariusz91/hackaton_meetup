package pl.jug.torun.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.jug.torun.Application;
import pl.jug.torun.domain.Participant;
import pl.jug.torun.repository.ParticipantRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ParticipantCreationServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private ParticipantCreationService participantCreationService;

    private Participant existing;

    private Participant notExisting;

    @Before
    public void setup() {
        existing = createParticipant("1");
        notExisting = createParticipant("2");

        participantRepository.save(existing);
    }

    @Test
    public void shouldCreateNonExistingUser() {
        List<Participant> downloadedParticipants = Arrays.asList(existing, notExisting);

        participantCreationService.createParticipantsIfNotExists(downloadedParticipants);

        assertEquals(2, participantRepository.count());
    }

    private Participant createParticipant(String memberId) {
        Participant participant = new Participant();
        participant.setMemberId(memberId);

        return participant;
    }

}