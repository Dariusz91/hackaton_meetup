package pl.jug.torun.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.jug.torun.Application;
import pl.jug.torun.domain.PrizeDefinition;
import pl.jug.torun.repository.PrizeDefinitionRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class PrizeDefinitionCreationServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static final String SAMPLE_NAME = "test";

    @Autowired
    private PrizeDefinitionCreationService prizeDefinitionCreationService;

    @Autowired
    private PrizeDefinitionRepository prizeDefinitionRepository;

    @Test
    public void shouldCreateNewPrize() {
        prizeDefinitionCreationService.createPrizeDefinition(SAMPLE_NAME);

        List<PrizeDefinition> results = prizeDefinitionRepository.findAll();
        assertEquals(1, results.size());
        assertEquals(SAMPLE_NAME, results.get(0).getName());
    }

}