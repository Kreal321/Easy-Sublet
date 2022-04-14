package com.example.easysublet.model;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class User implements Serializable {

    @Nullable
    private String username, email, uid;

    public User(){}

    public User(String username, String email, String uid) {
        this.username = username;
        this.email = email;
        this.uid = uid;
    }

    @Nullable
    public String getUid() {
        return uid;
    }

    @Nullable
    public String getUsername() {
        return username;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    @Nullable
    public void setEmail(String email) {
        this.email = email;
    }


    @Nullable
    public void setPassword(String uid) {
        this.uid = uid;
    }



}
