package pl.jug.torun.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.jug.torun.domain.PrizeDefinition;
import pl.jug.torun.repository.PrizeDefinitionRepository;
import pl.jug.torun.service.PrizeDefinitionCreationService;

import java.util.List;
import java.util.Map;

@RestController
public class PrizeController {

    @Autowired
    private PrizeDefinitionCreationService prizeDefinitionCreationService;

    @Autowired
    private PrizeDefinitionRepository prizeDefinitionRepository;

    @RequestMapping("/prize/add")
    public int createPrize(@RequestParam Map<String, String> params) {

        if (!params.containsKey("name")) {
            return -1;
        }

        String prizeName = params.get("name");
        prizeDefinitionCreationService.createPrizeDefinition(prizeName);

        return 200;
    }

    @RequestMapping("/prize/get/all")
    public String getAllPrizes() {
        List<PrizeDefinition> prizes = prizeDefinitionRepository.findAll();
        return convertToJson(prizes);
    }

    private String convertToJson(List<PrizeDefinition> prizes) {
        Gson gson = new Gson();

        JsonElement prizesJsonArray = gson.toJsonTree(prizes);

        JsonObject result = new JsonObject();
        result.add("prizes", prizesJsonArray);

        return result.toString();
    }
}
