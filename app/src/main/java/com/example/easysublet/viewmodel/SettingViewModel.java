package com.example.easysublet.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysublet.model.User;

public class SettingViewModel extends ViewModel {

    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    private static final String TAG = "MainViewModel";

    public MutableLiveData<User> getUser() {
        return userMutableLiveData;

    }

    public void setUser (String email) {
        // TODO: link this method with the firebase
        // Get one record from firebase where email equals the input one

        User user = new User("a", "a", "a");

        userMutableLiveData.setValue(user);

    }

    public void changePassword (String password) {
        // TODO: link this method with the firebase


    }

    public void deleteUser () {
        // TODO: link this method with the firebase

    }

}
