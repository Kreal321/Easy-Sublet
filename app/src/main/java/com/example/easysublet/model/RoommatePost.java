package com.example.easysublet.model;

import java.io.Serializable;

public class RoommatePost implements Serializable {
    private String index;
    private String username;
    private String image;
    private String title;
    private boolean active;
    private String address;
    private String contact;
    private int bathroomNum;
    private int bedroomNum;
    private boolean furnished;
    private String gender;
    private boolean pet;
    private int rent;
    private String time;
    private String other;

    public RoommatePost(){}

    public RoommatePost(String index, String username, String image, String title, boolean active, String address, String contact, int bathroomNum, int bedroomNum, boolean furnished, String gender, boolean pet, int rent, String time, String other) {
        this.index = index;
        this.username = username;
        this.image = image;
        this.title = title;
        this.active = active;
        this.address = address;
        this.contact = contact;
        this.bathroomNum = bathroomNum;
        this.bedroomNum = bedroomNum;
        this.furnished = furnished;
        this.gender = gender;
        this.pet = pet;
        this.rent = rent;
        this.time = time;
        this.other = other;
    }

    public String getIndex() {
        return index;
    }

    public String getUsername() {
        return username;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public boolean isActive() {
        return active;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public int getBathroomNum() {
        return bathroomNum;
    }

    public int getBedroomNum() {
        return bedroomNum;
    }

    public boolean isFurnished() {
        return furnished;
    }

    public String getGender() {
        return gender;
    }

    public boolean getPet() {
        return pet;
    }

    public int getRent() {
        return rent;
    }

    public String getTime() {
        return time;
    }

    public String getOther() {
        return other;
    }

    public void setIndex(String pid){
        index = pid;
    }
}
