package com.example.easysublet.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.easysublet.model.HomePost;
import com.example.easysublet.model.User;
import com.example.easysublet.repository.mainRepo;
import com.example.easysublet.repository.postRepo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class HomePostViewModel extends AndroidViewModel {

    private static final String TAG = "HomePostViewModel";
    private final MutableLiveData<HomePost> mPost;
    private final MutableLiveData<User> userMutableLiveData;
    private MutableLiveData<List<HomePost>> mPostList;
    private mainRepo accountRepo;
    private postRepo repository;
    private List<HomePost> postList;

    public HomePostViewModel(Application application) {
        super(application);
        repository = new postRepo(application);
        mPost = repository.getOneHomePostData();
        mPostList = repository.getHomePostData();
        accountRepo = new mainRepo(application);
        userMutableLiveData = accountRepo.getMutableLiveData();
        Log.d(TAG, "HomePostViewModel() is called");
    }

    public MutableLiveData<HomePost> getPost() {
        return mPost;
    }
    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }
    public MutableLiveData<List<HomePost>> getmPostList(){return mPostList;}

    public void fectchUser(){
        accountRepo.getAccount("");
    }

    public String getUid(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return firebaseUser.getUid();
    }

    public void getPostList(){
        repository.getHomePostList();
        repository.getHomePostData().observe(getApplication(), new Observer<List<HomePost>>() {
            @Override
            public void onChanged(List<HomePost> homePosts) {
                postList = homePosts;
            }
        });
        //postList = repository.getHomePostData().getValue();
    }

    public void setPost(String index) {
        repository.getHomePost(index);
        Log.d("IMAGE ON 1 POST", "TEST Failed");
    }

}
