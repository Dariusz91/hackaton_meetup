package pl.jug.torun.domain;

import javax.persistence.Entity;

@Entity
public class Event extends RootEntity {

    private String eventId;

    private String name;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
