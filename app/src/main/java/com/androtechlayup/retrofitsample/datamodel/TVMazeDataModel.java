
package com.androtechlayup.retrofitsample.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TVMazeDataModel {

    @SerializedName("person")
    @Expose
    private Person person;
    @SerializedName("character")
    @Expose
    private Character character;
    @SerializedName("self")
    @Expose
    private Boolean self;
    @SerializedName("voice")
    @Expose
    private Boolean voice;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Boolean getSelf() {
        return self;
    }

    public void setSelf(Boolean self) {
        this.self = self;
    }

    public Boolean getVoice() {
        return voice;
    }

    public void setVoice(Boolean voice) {
        this.voice = voice;
    }

}
