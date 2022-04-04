package com.example.easysublet.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.easysublet.model.HomePost;
import com.example.easysublet.model.RoommatePost;
import com.example.easysublet.repository.mainRepo;

public class HomePostViewModel extends AndroidViewModel {

    private static final String TAG = "HomePostViewModel";
    private final MutableLiveData<HomePost> mPost;

    public HomePostViewModel(Application application) {
        super(application);
        mPost = new MutableLiveData<>();

        Log.d(TAG, "HomePostViewModel() is called");
    }

    public void setPost(String index) {
        mPost.setValue(mainRepo.getHomePost(index));
    }

    public LiveData<HomePost> getPost() {
        return mPost;
    }
}
