package com.example.easysublet.model;

import androidx.annotation.Nullable;

public class User {

    @Nullable
    private String username, email, password;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    public User(){}

    @Nullable
    public String getUsername() {
        return username;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    @Nullable
    public void setUsername(String username) {
        this.username = username;
    }

    @Nullable
    public void setEmail(String email) {
        this.email = email;
    }

    @Nullable
    public boolean passwordIsCorrect(String password) {
        return this.password.equals(password);
    }

    @Nullable
    public void setPassword(String Password) {
        this.password = Password;
    }

    @Nullable
    public boolean updatePassword(String oldPassword, String newPassword) {
        if(!passwordIsCorrect(oldPassword)) {
            return false;
        }
        this.password = newPassword;
        return true;
    }


}
