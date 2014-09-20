package pl.jug.torun.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.jug.torun.Application;
import pl.jug.torun.domain.Event;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class EventsDownloadServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    String result = "{\"results\":[" +
            "{\"status\":\"upcoming\",\"visibility\":\"public\",\"maybe_rsvp_count\":0," +
            "\"id\":\"206212552\",\"utc_offset\":7200000,\"duration\":7200000,\"time\":1411574400000," +
            "\"waitlist_count\":0,\"updated\":1410321669000,\"yes_rsvp_count\":71,\"created\":1410288223000," +
            "\"name\":\"5. spotkanie Toruń JUG\",\"headcount\":0,\"group\":{\"id\":13468142," +
            "\"group_lat\":53.02000045776367,\"name\":\"Toruń JUG\",\"join_mode\":\"open\"," +
            "\"urlname\":\"Torun-JUG\",\"who\":\"Members\"}}]}";

    @Autowired
    private EventDownloadService eventDownloadService;

    @Test
    public void shouldReturnEvents() {
        eventDownloadService = new EventDownloadService();
        List<Event> events = eventDownloadService.responseToEventList(result);

        assertEquals(1, events.size());
        assertEquals("206212552", events.get(0).getEventId());
        assertEquals("5. spotkanie Toruń JUG", events.get(0).getName());
    }
}