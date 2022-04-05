package com.example.easysublet.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysublet.model.RoommatePost;
import com.example.easysublet.repository.mainRepo;

import java.util.List;

public class MyRoommatesViewModel extends ViewModel {

    private static final String TAG = "FindRoommatesViewModel";
    private final MutableLiveData<List<RoommatePost>> postList;

    public MyRoommatesViewModel() {
        postList = new MutableLiveData<>();
        postList.setValue(mainRepo.getMyRoommatePostList("Walter"));
        Log.d(TAG, "FindRoommatesViewModel() is called");
    }

    public LiveData<List<RoommatePost>> getPostList() {
        return this.postList;
    }

    public void getFilteredPostList(String title) {
        postList.setValue(mainRepo.getSearchedRoommatePostList(title));
    }
}