package pl.jug.torun.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.jug.torun.Application;
import pl.jug.torun.domain.Draw;
import pl.jug.torun.domain.Participant;
import pl.jug.torun.domain.PrizeDefinition;
import pl.jug.torun.repository.DrawRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class DrawCreationServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private DrawRepository drawRepository;

    private String eventId;
    private List<PrizeDefinition> prizes;
    private List<Participant> participants;

    @Autowired
    private DrawCreationService drawCreationService;

    private Draw draw;

    @Before
    public void setup() {
        draw = new Draw();

        eventId = "1234";
        prizes = new ArrayList<>();
        participants = new ArrayList<>();

        prizes.add(new PrizeDefinition());
        participants.add(new Participant());
        participants.add(new Participant());
    }

    @Test
    public void shouldCreateNewDraw() {
        drawCreationService.createDraw(eventId, prizes, participants);

        List<Draw> results = drawRepository.findAll();

        assertEquals(1, results.size());
        assertEquals(eventId, results.get(0).getEventId());
        assertEquals(1, results.get(0).getRemainingPrizes().size());
        assertEquals(2, results.get(0).getRemainingParticipants().size());
    }
}
