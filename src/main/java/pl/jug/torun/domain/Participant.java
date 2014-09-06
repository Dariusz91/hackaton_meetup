package pl.jug.torun.domain;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import pl.jug.torun.value.ParticipantSerializedNames;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Participant extends RootEntity {

    @Expose
    @SerializedName(ParticipantSerializedNames.MEMBER_ID_SERIALIZED_NAME)
    @Column(unique = true, nullable = false)
    private String memberId;

    @Expose
    @SerializedName(ParticipantSerializedNames.NAME_SERIALIZED_NAME)
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
