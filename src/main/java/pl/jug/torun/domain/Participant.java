package pl.jug.torun.domain;


import javax.persistence.Entity;

@Entity
public class Participant extends RootEntity {

    private String memberId;

    private String name;


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
