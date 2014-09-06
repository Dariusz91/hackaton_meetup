package pl.jug.torun.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.jug.torun.Application;
import pl.jug.torun.builders.ComponentBuilders;
import pl.jug.torun.domain.PrizeDefinition;
import pl.jug.torun.repository.PrizeDefinitionRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class PrizeDefinitionServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static final String SAMPLE_NAME = "test";

    @Autowired
    private PrizeDefinitionService prizeDefinitionService;

    @Autowired
    private PrizeDefinitionRepository prizeDefinitionRepository;

    @Autowired
    private ComponentBuilders builders;

    @Test
    public void shouldCreateNewPrize() {
        prizeDefinitionService.createPrizeDefinition(SAMPLE_NAME);

        List<PrizeDefinition> results = prizeDefinitionRepository.findAll();
        assertEquals(1, results.size());
        assertEquals(SAMPLE_NAME, results.get(0).getName());
    }

    @Test
    public void shouldRemovePrizeDefinition() {
        PrizeDefinition prize = builders.createPrizeDefinition("test");

        prizeDefinitionService.deletePrizeDefinition(prize.getId());

        assertEquals(0, prizeDefinitionRepository.count());
    }

}