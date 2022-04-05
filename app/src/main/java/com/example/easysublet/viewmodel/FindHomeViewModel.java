package com.example.easysublet.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysublet.model.HomePost;
import com.example.easysublet.model.RoommatePost;
import com.example.easysublet.repository.mainRepo;

import java.util.List;

public class FindHomeViewModel extends ViewModel {

    private static final String TAG = "FindHomeViewModel";
    private final MutableLiveData<List<HomePost>> postList;

    public FindHomeViewModel() {
        postList = new MutableLiveData<>();
        postList.setValue(mainRepo.getHomePostList());
        Log.d(TAG, "FindRoommatesViewModel() is called");
    }

    public LiveData<List<HomePost>> getPostList() {
        return this.postList;
    }

    public void getFilteredPostList(String title) {
        postList.setValue(mainRepo.getSearchedHomePostList(title));
    }
}