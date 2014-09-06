package pl.jug.torun.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.jug.torun.Application;
import pl.jug.torun.builders.ComponentBuilders;
import pl.jug.torun.domain.Draw;
import pl.jug.torun.domain.Participant;
import pl.jug.torun.domain.PrizeDefinition;
import pl.jug.torun.domain.ReceivedPrize;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ReceivedPrizeRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ReceivedPrizeRepository receivedPrizeRepository;

    @Autowired
    private ComponentBuilders componentBuilders;

    private Participant sampleParticipant;

    private Participant otherParticipant;

    private PrizeDefinition prizeDefinition;

    private Draw draw;

    @Before
    public void setup() {
        draw = componentBuilders.createDraw("1");

        sampleParticipant = componentBuilders.createParticipant("1");
        otherParticipant = componentBuilders.createParticipant("2");

        prizeDefinition = componentBuilders.createPrizeDefinition("test");
    }

    @Test
    public void shouldFindByParticipant() {
        ReceivedPrize prize = componentBuilders.createReceivedPrize(draw, sampleParticipant, prizeDefinition);
        componentBuilders.createReceivedPrize(draw, otherParticipant, prizeDefinition);
        componentBuilders.createReceivedPrize(draw, otherParticipant, prizeDefinition);

        List<ReceivedPrize> results = receivedPrizeRepository.findByParticipant(sampleParticipant);

        assertEquals(1, results.size());
        assertEquals(prize.getId(), results.get(0).getId());
    }

}