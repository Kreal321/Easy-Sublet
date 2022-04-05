package com.example.easysublet.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysublet.model.HomePost;
import com.example.easysublet.model.RoommatePost;
import com.example.easysublet.repository.mainRepo;

import java.util.List;

public class FindRoommatesViewModel extends ViewModel {

    private static final String TAG = "FindRoommatesViewModel";
    private final MutableLiveData<List<RoommatePost>> postList;

    public FindRoommatesViewModel() {
        postList = new MutableLiveData<>();
        postList.setValue(mainRepo.getRoommatePostList());
        Log.d(TAG, "FindRoommatesViewModel() is called");
    }

    public LiveData<List<RoommatePost>> getPostList() {
        return this.postList;
    }

    public void getFilteredPostList(String title) {
        postList.setValue(mainRepo.getSearchedRoommatePostList(title));
    }
}