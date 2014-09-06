package pl.jug.torun.domain;


public class Prize {

    private PrizeDefinition prizeDefinition;

    private int count;

    public Prize(PrizeDefinition prizeDefinition) {
        this.prizeDefinition = prizeDefinition;
        count = 0;
    }

    public PrizeDefinition getPrizeDefinition() {
        return prizeDefinition;
    }

    public void setPrizeDefinition(PrizeDefinition prizeDefinition) {
        this.prizeDefinition = prizeDefinition;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
