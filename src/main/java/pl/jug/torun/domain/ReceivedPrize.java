package pl.jug.torun.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ReceivedPrize extends RootEntity {

    @ManyToOne(targetEntity = Draw.class)
    private Draw draw;

    @ManyToOne(targetEntity = Participant.class)
    private Participant participant;

    @ManyToOne(targetEntity = PrizeDefinition.class)
    private PrizeDefinition prizeDefinition;

    public Draw getDraw() {
        return draw;
    }

    public void setDraw(Draw draw) {
        this.draw = draw;
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
