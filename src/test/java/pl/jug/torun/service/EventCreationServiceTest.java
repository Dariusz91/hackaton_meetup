package pl.jug.torun.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.jug.torun.Application;
import pl.jug.torun.builders.ComponentBuilders;
import pl.jug.torun.domain.Event;
import pl.jug.torun.repository.EventRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class EventCreationServiceTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private EventCreationService eventCreationService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ComponentBuilders builders;

    private Event existingEvent;

    @Before
    public void setup() {
        existingEvent = builders.createEvent("123", "test");
    }

    @Test
    public void shouldAddOnlyNewEventsToDb() {
        long initialCount = eventRepository.count();
        List<Event> eventsList = Arrays.asList(existingEvent, getNewEvent("333", "test"));

        eventCreationService.createOnlyNewEvents(eventsList);

        assertEquals(initialCount + 1, eventRepository.count());
    }

    private Event getNewEvent(String eventId, String name) {
        Event event = new Event();
        event.setEventId(eventId);
        event.setName(name);

        return event;
    }
}