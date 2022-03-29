package com.example.easysublet.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FindRoomatesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FindRoomatesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Find roommates fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}