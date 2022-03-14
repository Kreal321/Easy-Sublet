package com.example.easysublet.viewmodel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysublet.model.User;

    public class SignUpViewModel extends ViewModel {
    private MutableLiveData<User> userMutableLiveData;
    private static final String TAG = "SignUpViewModel";

    public MutableLiveData<User> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public boolean addUser (String username, String email, String password) {
        // TODO: link this method with the firebase when email is not in the firebase

        // Email checking
        if(email.equals("a")) return false;

        // Add this record to firebase
        User user = new User(username, email, password);
        userMutableLiveData.setValue(user);
        return true;

    }

}
