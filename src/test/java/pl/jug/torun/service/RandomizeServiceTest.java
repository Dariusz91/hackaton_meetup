package pl.jug.torun.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.jug.torun.Application;
import pl.jug.torun.builders.ComponentBuilders;
import pl.jug.torun.domain.Draw;
import pl.jug.torun.domain.Participant;
import pl.jug.torun.domain.PrizeDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class RandomizeServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private RandomizeService randomizeService;

    @Autowired
    private ComponentBuilders builders;

    private Participant firstParticipant;

    private Participant secondParticipant;

    private PrizeDefinition firstPrize;

    private PrizeDefinition secondPrize;

    private Draw draw;

    private Random mockRandom;

    @Before
    public void setup() {
        firstParticipant = builders.createParticipant("1");
        secondParticipant = builders.createParticipant("2");

        firstPrize = builders.createPrizeDefinition("test");
        secondPrize = builders.createPrizeDefinition("123");

        draw = builders.createDraw("12345");
        draw.setRemainingParticipants(prepareParticipantsList());
        draw.setRemainingPrizes(preparePrizeDefinitionList());

        mockRandom = mock(Random.class);
    }

    @Test
    public void shouldReturnFirstPrizeFromList() {
        Optional<PrizeDefinition> result = randomizeService.getNextPrize(draw);

        assertTrue(result.isPresent());
        assertEquals(draw.getRemainingPrizes().get(0).getId(), result.get().getId());
    }

    @Test
    public void shouldNotReturnParticipantWhoAlreadyHaveSameAward() {
        builders.createReceivedPrize(draw, firstParticipant, firstPrize);
        when(mockRandom.nextInt(anyInt())).thenReturn(0);
        Whitebox.setInternalState(randomizeService, "random", mockRandom);

        Participant participant = randomizeService.randomParticipant(draw);

        assertNotEquals(participant.getId(), firstParticipant.getId());
    }

    @Test
    public void shouldRemovePrizeAfterExecution() {
        PrizeDefinition prize = randomizeService.getNextPrize(draw).get();

        randomizeService.randomParticipant(draw, prize);

        assertFalse(draw.getRemainingPrizes().contains(prize));
    }

    @Test
    public void shouldRemoveParticipantAfterExecution() {
        Participant participant = randomizeService.randomParticipant(draw);

        assertFalse(draw.getRemainingParticipants().contains(participant));
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