package com.example.easysublet.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.easysublet.model.User;
import com.example.easysublet.repository.mainRepo;

public class MainViewModel extends AndroidViewModel {

    private MutableLiveData<User> userData;
    private static final String TAG = "MainViewModel";
    private mainRepo repository;
    private MutableLiveData<Boolean> status;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new mainRepo(application);
        userData = repository.getMutableLiveData();
        status = repository.getUserLogData();
    }

    public MutableLiveData<User> getUser() {
        return userData;
    }
    public MutableLiveData<Boolean> getStatus(){
        return status;
    }


    public void setUser (String email, String password) {
        // TODO: link this method with the firebase
        // Get one record from firebase where email equals the input one
        repository.login(email,password);
    }

}
