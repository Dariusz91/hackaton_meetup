package pl.jug.torun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jug.torun.domain.Draw;
import pl.jug.torun.domain.ReceivedPrize;
import pl.jug.torun.repository.ReceivedPrizeRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PrizeHistoryService {

    @Autowired
    private ReceivedPrizeRepository receivedPrizeRepository;

    @Transactional
    public Map<String, List<String>> createHistoryMap() {
        List<ReceivedPrize> receivedPrizes = receivedPrizeRepository.findAll();

        Map<String, List<String>> result = new HashMap<>();

        for (ReceivedPrize prize : receivedPrizes) {
            Draw draw = prize.getDraw();
            List<String> drawList = getListForDraw(draw, result);
            drawList.add(prize.toJson());
        }

        return result;
    }

    private List<String> getListForDraw(Draw draw, Map<String, List<String>> result) {
        if (!result.containsKey(draw.getEventId())) {
            result.put(draw.getEventId(), new ArrayList<>());
        }

        return result.get(draw.getEventId());
    }
}
