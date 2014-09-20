package pl.jug.torun.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jug.torun.domain.Event;
import pl.jug.torun.repository.EventRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EventCreationService {

    @Autowired
    private EventRepository eventRepository;

    @Transactional
    public void createOnlyNewEvents(List<Event> events) {
        events.stream().forEach(this::createIfNotExists);
    }

    private void createIfNotExists(Event event) {
        Event fromDb = eventRepository.findByEventId(event.getEventId());

        if (fromDb == null) {
            eventRepository.save(event);
        }
    }

}
