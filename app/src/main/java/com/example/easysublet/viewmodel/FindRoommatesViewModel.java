package com.example.easysublet.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FindRoommatesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FindRoommatesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Find roommates fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}