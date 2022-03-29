package com.example.easysublet.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FindHomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FindHomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Find home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}