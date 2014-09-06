package pl.jug.torun.domain;


import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Draw extends RootEntity {

    @OneToMany
    private List<PrizeDefinition> remainingPrizes;

    @OneToMany
    private List<Participant> remainingParticipants;

    private boolean enabled = true;

    private String eventId;

    public List<PrizeDefinition> getRemainingPrizes() {
        return remainingPrizes;
    }

    public void setRemainingPrizes(List<PrizeDefinition> remainingPrizes) {
        this.remainingPrizes = remainingPrizes;
    }

    public List<Participant> getRemainingParticipants() {
        return remainingParticipants;
    }

    public void setRemainingParticipants(List<Participant> remainingParticipants) {
        this.remainingParticipants = remainingParticipants;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
