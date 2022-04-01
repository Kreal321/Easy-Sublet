package com.example.easysublet.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapViewModel extends ViewModel {

    private static final String TAG = "MapViewModel";
    private final MutableLiveData<String> mText;

    public MapViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Map page");
        Log.d(TAG, "MapViewModel() is called");
    }

    public LiveData<String> getText() {
        return mText;
    }
}