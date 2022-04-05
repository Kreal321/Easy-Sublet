package com.example.easysublet.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.easysublet.model.RoommatePost;
import com.example.easysublet.repository.mainRepo;

public class EditRoommatePostViewModel extends AndroidViewModel {

    private static final String TAG = "RoommatePostViewModel";
    private final MutableLiveData<RoommatePost> mPost;

    public EditRoommatePostViewModel(Application application) {
        super(application);
        mPost = new MutableLiveData<>();

        Log.d(TAG, "RoommatePostViewModel() is called");
    }

    public void setPost(String index) {
        mPost.setValue(mainRepo.getRoommatePost(index));
    }

    public LiveData<RoommatePost> getPost() {
        return mPost;
    }
}
