package pl.jug.torun.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ReceivedPrize extends RootEntity {

    private String eventId;

    @ManyToOne(targetEntity = Participant.class)
    private Participant participant;

    @ManyToOne(targetEntity = PrizeDefinition.class)
    private PrizeDefinition prizeDefinition;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public PrizeDefinition getPrizeDefinition() {
        return prizeDefinition;
    }

    public void setPrizeDefinition(PrizeDefinition prizeDefinition) {
        this.prizeDefinition = prizeDefinition;
    }
}
