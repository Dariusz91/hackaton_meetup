package pl.jug.torun.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.jug.torun.Application;
import pl.jug.torun.domain.Participant;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ParticipantDownloadServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ParticipantDownloadService participantDownloadService;

    private String result = "{\"results\":[" +
            "{\"response\":\"yes\",\"member\":{\"name\":\"Ala Kot\",\"member_id\":\"1234\"}}, " +
            "{\"response\":\"no\",\"member\":{\"name\":\"Jan Nowak\",\"member_id\":\"2222\"}}" +
            "]}";

    @Test
    public void shouldReturnOnGoingParticipants() {
        List<Participant> participants = participantDownloadService.responseToParticipantList(result);

        assertEquals(1, participants.size());
    }
}