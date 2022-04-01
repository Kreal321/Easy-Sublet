package com.example.easysublet.model;

public class RoommatePost {
    private int index;
    private String username;
    private int image;
    private String title;
    private boolean active;
    private String address;
    private String contact;
    private int bathroomNum;
    private int bedroomNum;
    private boolean furnished;
    private String gender;
    private String pet;
    private int rent;
    private String time;
    private String other;

    public RoommatePost(int index, String username, int image, String title, boolean active, String address, String contact, int bathroomNum, int bedroomNum, boolean furnished, String gender, String pet, int rent, String time, String other) {
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

    public int getIndex() {
        return index;
    }

    public String getUsername() {
        return username;
    }

    public int getImage() {
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

    public String getPet() {
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
}
