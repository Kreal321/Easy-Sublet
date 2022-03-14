package com.example.easysublet.viewmodel;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysublet.model.User;

public class MainViewModel extends ViewModel {

    private MutableLiveData<User> userMutableLiveData;
    private static final String TAG = "MainViewModel";

    public MutableLiveData<User> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public boolean setUser (String email, String password) {
        // TODO: link this method with the firebase
        // Get one record from firebase where email equals the input one
        User user = new User(null, null, null);
        if (email.equals("a")) {
            user = new User("a", "a", "a");
        } else{
            return false;
        }

        // Password checking
        if(!user.passwordIsCorrect(password)) return false;

        userMutableLiveData.setValue(user);
        return true;
    }

}
