package pl.jug.torun.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.jug.torun.domain.PrizeDefinition;
import pl.jug.torun.repository.PrizeDefinitionRepository;
import pl.jug.torun.service.PrizeDefinitionService;

import java.util.List;
import java.util.Map;

@RestController
public class PrizeController {

    @Autowired
    private PrizeDefinitionService prizeDefinitionService;

    @Autowired
    private PrizeDefinitionRepository prizeDefinitionRepository;

    @RequestMapping(value = "/prize/add", produces = "application/json;charset=UTF-8")
    public String createPrize(@RequestParam Map<String, String> params) {

        if (!params.containsKey("name")) {
            return "{\"error\":\"error\"}";
        }

        String prizeName = params.get("name");
        PrizeDefinition prize = prizeDefinitionService.createPrizeDefinition(prizeName);

        JsonObject prizeJson = new JsonObject();
        prizeJson.addProperty("id", Long.toString(prize.getId()));

        return prizeJson.toString();
    }

    @RequestMapping(value = "/prize/get/all", produces = "application/json;charset=UTF-8")
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

    @RequestMapping(value = "/prize/delete/{id}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String remove(@PathVariable Long id) {
        if (prizeDefinitionRepository.exists(id)) {
            prizeDefinitionService.deletePrizeDefinition(id);

            return "{\"status\":\"success\"}";
        } else {
            return "{\"error\":\"id nie istnieje\"}";
        }
    }
}
