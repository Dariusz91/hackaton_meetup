package pl.jug.torun.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.jug.torun.Application;
import pl.jug.torun.builders.ComponentBuilders;
import pl.jug.torun.domain.Participant;

import static org.junit.Assert.*;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ParticipantRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private ComponentBuilders builders;

    @Test
    public void shouldFindUserByMemberId() {
        Participant firstParticipant = builders.createParticipant("1");
        builders.createParticipant("2");

        Participant result = participantRepository.findByMemberId(firstParticipant.getMemberId());

        assertNotNull(result);
        assertEquals(firstParticipant.getId(), result.getId());
    }

    @Test
    public void shouldReturnNullWhenMemberIdNotExists() {
        Participant result = participantRepository.findByMemberId("nonExisting");

        assertNull(result);
    }
}