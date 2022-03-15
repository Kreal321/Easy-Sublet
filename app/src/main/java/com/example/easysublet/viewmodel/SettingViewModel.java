package com.example.easysublet.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.easysublet.model.User;
import com.example.easysublet.repository.mainRepo;

public class SettingViewModel extends AndroidViewModel {

    private MutableLiveData<User> userMutableLiveData;
    private static final String TAG = "MainViewModel";
    private mainRepo repository;
    public MutableLiveData<User> getUser() {
        return userMutableLiveData;

    }

    public SettingViewModel(@NonNull Application application){
        super(application);
        repository = new mainRepo(application);
        userMutableLiveData = repository.getMutableLiveData();
    }

    public void setUser (String email) {
        // TODO: link this method with the firebase
        // Get one record from firebase where email equals the input one
        repository.getAccount(email);


    }

    public void changePassword (String password) {
        // TODO: link this method with the firebase
        repository.changePassword(password);

    }

    public void deleteUser () {
        // TODO: link this method with the firebase
        Log.d("delteUser","HERE");
        repository.deleteAccount();
    }

}
