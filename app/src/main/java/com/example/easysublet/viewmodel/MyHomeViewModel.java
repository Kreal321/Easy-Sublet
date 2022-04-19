package com.example.easysublet.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.easysublet.model.HomePost;
import com.example.easysublet.repository.postRepo;

import java.util.List;

public class MyHomeViewModel extends AndroidViewModel {

    private static final String TAG = "MyHomeViewModel";
    private final MutableLiveData<String> mText;
    private MutableLiveData<List<HomePost>> mList;
    //private mainRepo repository;
    private postRepo repo;

    public MyHomeViewModel(@NonNull Application application) {
        super(application);

        repo = new postRepo(application);
        mList= repo.getHomePostData();
        mText = new MutableLiveData<>();
        mText.setValue("This is my home fragment");
        Log.d(TAG, "MyHomeViewModel() is called");

    }
    public void searchMyHomePosts(String username){
        repo.getMyHomePostList(username);
    }

    public void startFecthList(String uid){
        repo.getMyHomePostList(uid);
    }

    public MutableLiveData<List<HomePost>> getPostList() {
        return mList;
    }

    public void getFilteredPostList(String title) {
        repo.getSearchedHomePostList(title);
    }



}