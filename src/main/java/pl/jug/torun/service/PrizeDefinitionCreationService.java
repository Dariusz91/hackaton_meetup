package pl.jug.torun.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jug.torun.domain.PrizeDefinition;
import pl.jug.torun.repository.PrizeDefinitionRepository;

@Service
public class PrizeDefinitionCreationService {

    @Autowired
    private PrizeDefinitionRepository prizeDefinitionRepository;

    @Transactional
    public PrizeDefinition createPrizeDefinition(String name) {
        PrizeDefinition prizeDefinition = new PrizeDefinition();
        prizeDefinition.setName(name);

        prizeDefinitionRepository.save(prizeDefinition);

        return prizeDefinition;
    }

}
