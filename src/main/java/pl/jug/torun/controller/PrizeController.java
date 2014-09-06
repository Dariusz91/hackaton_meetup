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
    public String createPrize(@RequestParam Map<String, String> params) {

        if (!params.containsKey("name")) {
            return "{\"error\":\"error\"}";
        }

        String prizeName = params.get("name");
        PrizeDefinition prize = prizeDefinitionCreationService.createPrizeDefinition(prizeName);

        JsonObject prizeJson = new JsonObject();
        prizeJson.addProperty("id", Long.toString(prize.getId()));

        return prizeJson.toString();
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
