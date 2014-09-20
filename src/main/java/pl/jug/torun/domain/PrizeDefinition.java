package pl.jug.torun.domain;

import com.google.gson.annotations.Expose;

import javax.persistence.Entity;

@Entity
public class PrizeDefinition extends RootEntity {

    @Expose
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
