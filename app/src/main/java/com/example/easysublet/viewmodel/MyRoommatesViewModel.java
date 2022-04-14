package com.example.easysublet.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.easysublet.model.RoommatePost;
import com.example.easysublet.repository.postRepo;

import java.util.List;

public class MyRoommatesViewModel extends AndroidViewModel {

    private static final String TAG = "MyRoommatesViewModel";
    private final MutableLiveData<String> mText;
    private MutableLiveData<List<RoommatePost>> mList;
    //private mainRepo repository;
    private postRepo repo;

    public MyRoommatesViewModel(@NonNull Application application) {
        super(application);
        repo = new postRepo(application);
        repo.getRoommatePostList();
        mList= repo.getRoommatePostData();
        mText = new MutableLiveData<>();
        mText.setValue("This is MyRoommatesViewModel");
        Log.d(TAG, "MyRoommatesViewModel() is called");

    }

    public void searchMyHomePosts(String username){
        repo.getMyRoommatePostList(username);
    }

    public void startFectchList(){
        repo.getMyRoommatePostList("");
    }
    public MutableLiveData<List<RoommatePost>> getPostList() {
        return mList;
    }
//    public LiveData<String> getText() {
//        return mText;
//    }

    public void getFilteredPostList(String title) {
        repo.getSearchedRoommatePostList(title);
    }


}