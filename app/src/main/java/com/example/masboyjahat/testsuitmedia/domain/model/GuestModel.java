package com.example.masboyjahat.testsuitmedia.domain.model;

/**
 * Created by masboy jahat on 8/4/2016.
 */
public class GuestModel {
    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    private String name, birthdate;
    private int image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
