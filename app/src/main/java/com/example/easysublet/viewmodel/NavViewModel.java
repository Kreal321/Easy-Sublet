package com.example.easysublet.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.easysublet.model.User;
import com.example.easysublet.repository.mainRepo;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<User> userMutableLiveData;
    private static final String TAG = "MainViewModel";
    private mainRepo repository;

    public HomeViewModel(@NonNull Application application){
        super(application);
        repository = new mainRepo(application);
        userMutableLiveData = repository.getMutableLiveData();
    }

    public MutableLiveData<User> getUser() {
        return userMutableLiveData;
    }

    public void setUser (String email) {
        // TODO: link this method with the firebase
        // Get one record from firebase where email equals the input one
        repository.getAccount(email);


    }

}
