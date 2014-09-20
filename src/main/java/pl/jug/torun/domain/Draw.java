package pl.jug.torun.domain;


import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
public class Draw extends RootEntity {

    @ManyToMany
    private List<PrizeDefinition> remainingPrizes;

    @ManyToMany
    private List<Participant> remainingParticipants;

    private boolean enabled = true;

    @ManyToOne
    private Event event;

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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
