package pl.jug.torun.domain;

import javax.persistence.Entity;

@Entity
public class PrizeDefinition extends RootEntity {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
