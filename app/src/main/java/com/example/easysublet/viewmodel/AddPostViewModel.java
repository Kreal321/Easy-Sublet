package com.example.easysublet.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddPostViewModel extends ViewModel {

    private static final String TAG = "AddPostViewModel";
    private final MutableLiveData<String> mText;

    public AddPostViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Add Post fragment");
        Log.d(TAG, "AddPostViewModel() is called");
    }

    public LiveData<String> getText() {
        return mText;
    }
}