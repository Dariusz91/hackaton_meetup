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
import pl.jug.torun.domain.*;
import pl.jug.torun.repository.ReceivedPrizeRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class PrizeAcceptanceServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private PrizeAcceptanceService prizeAcceptanceService;

    @Autowired
    private ReceivedPrizeRepository receivedPrizeRepository;

    @Autowired
    private ComponentBuilders builders;

    private Participant firstParticipant;

    private Participant secondParticipant;

    private PrizeDefinition firstPrize;

    private PrizeDefinition secondPrize;

    private Draw draw;

    @Before
    public void setup() {
        firstParticipant = builders.createParticipant("1");
        secondParticipant = builders.createParticipant("2");

        firstPrize = builders.createPrizeDefinition("test");
        secondPrize = builders.createPrizeDefinition("123");

        Event event = builders.createEvent("12345", "12345");
        draw = builders.createDraw(event);
        draw.setRemainingParticipants(prepareParticipantsList());
        draw.setRemainingPrizes(preparePrizeDefinitionList());
    }

    @Test
    public void shouldDeleteParticipantAfterAccept() {
        prizeAcceptanceService.acceptParticipant(firstParticipant, draw, firstPrize);

        assertFalse(draw.getRemainingParticipants().contains(firstParticipant));
    }

    @Test
    public void shouldDeleteParticipantAfterDiscard() {
        prizeAcceptanceService.discardParticipant(firstParticipant, draw);

        assertFalse(draw.getRemainingParticipants().contains(firstParticipant));
    }

    @Test
    public void shouldDeletePrizeAfterAccept() {
        prizeAcceptanceService.acceptParticipant(firstParticipant, draw, firstPrize);

        assertFalse(draw.getRemainingPrizes().contains(firstPrize));
    }

    @Test
    public void shouldKeepPrizeAfterDiscard() {
        prizeAcceptanceService.discardParticipant(firstParticipant, draw);

        assertTrue(draw.getRemainingPrizes().contains(firstPrize));
    }

    @Test
    public void shouldCreateReceivedPrizeAfterAcceptance() {
        prizeAcceptanceService.acceptParticipant(firstParticipant, draw, firstPrize);

        List<ReceivedPrize> receivedPrizes = receivedPrizeRepository.findAll();
        assertEquals(1, receivedPrizes.size());
        assertEquals(firstParticipant, receivedPrizes.get(0).getParticipant());
        assertEquals(draw, receivedPrizes.get(0).getDraw());
    }

    private List<Participant> prepareParticipantsList() {
        List<Participant> participants = new ArrayList<>();

        participants.add(firstParticipant);
        participants.add(secondParticipant);

        return participants;
    }

    private List<PrizeDefinition> preparePrizeDefinitionList() {
        List<PrizeDefinition> prizes = new ArrayList<>();

        prizes.add(firstPrize);
        prizes.add(secondPrize);

        return prizes;
    }
}