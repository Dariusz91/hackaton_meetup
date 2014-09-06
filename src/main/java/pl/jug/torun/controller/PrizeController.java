package pl.jug.torun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.jug.torun.service.PrizeDefinitionCreationService;

import java.util.Map;

@RestController
public class PrizeController {

    @Autowired
    private PrizeDefinitionCreationService prizeDefinitionCreationService;

    @RequestMapping("/prizes")
    public int createPrize(@RequestParam Map<String, String> params) {

        if (!params.containsKey("name")) {
            return -1;
        }

        String prizeName = params.get("name");
        prizeDefinitionCreationService.createPrizeDefinition(prizeName);

        return 200;
    }
}
