package com.example.easysublet.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.easysublet.model.Post;
import com.example.easysublet.repository.mainRepo;

public class RoommatePostViewModel extends AndroidViewModel {

    private static final String TAG = "RoommatePostViewModel";
    private final MutableLiveData<Post> mPost;

    public RoommatePostViewModel(Application application) {
        super(application);
        mPost = new MutableLiveData<>();

        Log.d(TAG, "RoommatePostViewModel() is called");
    }

    public void setPost(int index) {
        mPost.setValue(mainRepo.getPost(index));
    }

    public LiveData<Post> getPost() {
        return mPost;
    }
}
