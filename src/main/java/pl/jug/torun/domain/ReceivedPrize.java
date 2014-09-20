package pl.jug.torun.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ReceivedPrize extends RootEntity {

    private static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    @ManyToOne(targetEntity = Draw.class)
    private Draw draw;

    @Expose
    @ManyToOne(targetEntity = Participant.class)
    private Participant participant;

    @Expose
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

    public String toJson() {
        return gson.toJson(this);
    }
}
