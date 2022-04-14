package com.example.easysublet.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.easysublet.model.RoommatePost;
import com.example.easysublet.repository.postRepo;

import java.util.List;

public class FindRoommatesViewModel extends AndroidViewModel {

//    private static final String TAG = "FindRoommatesViewModel";
//    private final MutableLiveData<List<RoommatePost>> postList;

//    public FindRoommatesViewModel() {
//        postList = new MutableLiveData<>();
//        postList.setValue(mainRepo.getRoommatePostList());
//        Log.d(TAG, "FindRoommatesViewModel() is called");
//    }

//    public LiveData<List<RoommatePost>> getPostList() {
//        return this.postList;
//    }

//    public void getFilteredPostList(String title) {
//        postList.setValue(mainRepo.getSearchedRoommatePostList(title));
//    }


    //Work Below----------------
    private static final String TAG = "FindRoommatesViewModel";
    private final MutableLiveData<String> mText;
    private MutableLiveData<List<RoommatePost>> mList;
    //private mainRepo repository;
    private postRepo repo;

    public FindRoommatesViewModel(@NonNull Application application) {
        super(application);

        repo = new postRepo(application);
        repo.getRoommatePostList();
        mList= repo.getRoommatePostData();
        mText = new MutableLiveData<>();
        mText.setValue("This is Find roommate fragment");
        Log.d(TAG, "FindRoommatesViewModel() is called");

    }
    public MutableLiveData<List<RoommatePost>> getPostList() {
        repo.getRoommatePostList();
        return mList;
    }
    public LiveData<String> getText() {
        return mText;
    }

    public void getFilteredPostList(String title) {
        repo.getSearchedRoommatePostList(title);
    }

}