package com.example.easysublet.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.easysublet.model.HomePost;
import com.example.easysublet.model.RoommatePost;
import com.example.easysublet.model.User;
import com.example.easysublet.repository.mainRepo;
import com.example.easysublet.repository.postRepo;

import java.util.List;

public class ProfileViewModel extends AndroidViewModel {

    private MutableLiveData<User> userMutableLiveData;
    private static final String TAG = "ProfileViewModel";
    private mainRepo repository;
    private postRepo postRepository;
    private MutableLiveData<List<HomePost>> homePostList;
    private MutableLiveData<List<RoommatePost>> roommatePostList;
    public MutableLiveData<User> getUser() {
        return userMutableLiveData;
    }
    public MutableLiveData<List<HomePost>> getHomePostList(){
        return homePostList;
    }
    public MutableLiveData<List<RoommatePost>> getRoommatePostList(){
        return roommatePostList;
    }

    public ProfileViewModel(@NonNull Application application){
        super(application);
        repository = new mainRepo(application);
        postRepository = new postRepo(application);
        userMutableLiveData = repository.getMutableLiveData();
        homePostList = postRepository.getHomePostData();
        roommatePostList = postRepository.getRoommatePostData();
    }

    public void setUser (String email) {
        // TODO: link this method with the firebase
        // Get one record from firebase where email equals the input one
        repository.getAccount(email);

    }

    public void updateInfo (String username, String email) {
        // TODO: link this method with the mainRepo
        Log.d(TAG, "updateInfo() is called" + username + email);
        repository.updateEmail(email);
    }

    public void changePassword(String oldPassword, String newPassword){
        repository.changePassword(oldPassword, newPassword);
    }

    public void logout(){
        repository.logOut();
    }

    public void deleteUser () {
        // TODO: link this method with the firebase
        Log.d("delteUser","HERE");
        repository.deleteAccount();
    }

    public void getMyListCount() {
        postRepository.getMyHomePostList("");
        postRepository.getMyRoommatePostList("");
    }
}