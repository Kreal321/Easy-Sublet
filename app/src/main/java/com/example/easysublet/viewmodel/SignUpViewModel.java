package com.example.easysublet.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.easysublet.model.User;
import com.example.easysublet.repository.mainRepo;

public class SignUpViewModel extends AndroidViewModel {
    private MutableLiveData<User> userMutableLiveData;
    private static final String TAG = "SignUpViewModel";
    private mainRepo repository;

    public SignUpViewModel(@NonNull Application application){
        super(application);
        repository = new mainRepo(application);
        userMutableLiveData = repository.getMutableLiveData();

    }

    public MutableLiveData<User> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public boolean addUser (String username, String email, String password) {
        // TODO: link this method with the firebase when email is not in the firebase
        repository.signUp(username,email,password);
        // Email checking
        //if(email.equals("a")) return false;

        // Add this record to firebase
        //User user = new User(username, email, password);
        //userMutableLiveData.setValue(user);
        return true;

    }

}
