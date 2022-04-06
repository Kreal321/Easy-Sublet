package com.example.easysublet.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysublet.model.HomePost;
import com.example.easysublet.model.RoommatePost;
import com.example.easysublet.repository.mainRepo;

import java.util.List;

public class MyHomeViewModel extends ViewModel {

    private static final String TAG = "FindHomeViewModel";
    private MutableLiveData<List<HomePost>> postList;

    public MyHomeViewModel() {
        Log.d(TAG, "FindRoommatesViewModel() is called");
    }

    public LiveData<List<HomePost>> getPostList(String username) {
        if (postList == null) {
            postList = new MutableLiveData<>();
            postList.setValue(mainRepo.getMyHomePostList(username));
        }
        return postList;

    }


}