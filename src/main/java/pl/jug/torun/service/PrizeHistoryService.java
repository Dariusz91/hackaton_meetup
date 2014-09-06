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
    public Map<Draw, List<ReceivedPrize>> createHistoryMap() {
        List<ReceivedPrize> receivedPrizes = receivedPrizeRepository.findAll();

        Map<Draw, List<ReceivedPrize>> result = new HashMap<>();

        for (ReceivedPrize prize : receivedPrizes) {
            Draw draw = prize.getDraw();
            List<ReceivedPrize> drawList = getListForDraw(draw, result);
            drawList.add(prize);
        }

        return result;
    }

    private List<ReceivedPrize> getListForDraw(Draw draw, Map<Draw, List<ReceivedPrize>> result) {
        if (!result.containsKey(draw)) {
            result.put(draw, new ArrayList<>());
        }

        return result.get(draw);
    }
}
