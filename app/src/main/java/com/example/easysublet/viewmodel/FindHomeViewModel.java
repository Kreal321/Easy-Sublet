package com.example.easysublet.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.easysublet.model.HomePost;
import com.example.easysublet.repository.postRepo;

import java.util.List;

public class FindHomeViewModel extends AndroidViewModel {

    private static final String TAG = "FindHomeViewModel";
    private final MutableLiveData<String> mText;
    private MutableLiveData<List<HomePost>> mList;
    //private mainRepo repository;
    private postRepo repo;

    public FindHomeViewModel(@NonNull Application application) {
        super(application);

        repo = new postRepo(application);
        //repo.cleanData();
        //repo.getHomePostList();
        mList= repo.getHomePostData();
        mText = new MutableLiveData<>();
        mText.setValue("This is Find home fragment");
        Log.d(TAG, "FindHomeViewModel() is called");

    }
    public void startFecthList(){
        repo.getHomePostList();
    }
    public MutableLiveData<List<HomePost>> getHomePostList() {
        return mList;
    }
    public LiveData<String> getText() {
        return mText;
    }

    public void getFilteredPostList(String title) {
        repo.getSearchedHomePostList(title);
    }

}