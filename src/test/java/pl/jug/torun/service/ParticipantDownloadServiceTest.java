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

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ParticipantDownloadServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ParticipantDownloadService participantDownloadService;

    @Test
    public void myOwnTest() {
        String key = "371725c52607a49516b651f831156";
        String eventId = "201836452";

        List<Participant> participants = participantDownloadService.downloadParticipants(key, eventId, "name");
        for (Participant participant : participants) {
            System.out.println(participant.getMemberId() + " " + participant.getName());
        }
    }
}