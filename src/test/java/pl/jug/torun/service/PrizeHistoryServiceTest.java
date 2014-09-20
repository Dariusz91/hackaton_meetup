package pl.jug.torun.service;

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

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class PrizeHistoryServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private PrizeHistoryService prizeHistoryService;

    @Autowired
    private ComponentBuilders builders;

    @Before
    public void setup() {
        Participant participant = builders.createParticipant("1");
        PrizeDefinition prizeDefinition = builders.createPrizeDefinition("test");

        Draw firstDraw = builders.createDraw("1");
        Draw secondDraw = builders.createDraw("2");

        builders.createReceivedPrize(firstDraw, participant, prizeDefinition);
        builders.createReceivedPrize(secondDraw, participant, prizeDefinition);
    }

    @Test
    public void shouldCreateHistoryMap() {
        Map<String, List<String>> result = prizeHistoryService.createHistoryMap();

        assertEquals(2, result.size());
    }
}