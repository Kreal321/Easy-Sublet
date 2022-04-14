package com.example.easysublet.viewmodel;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.easysublet.R;
import com.example.easysublet.model.RoommatePost;
import com.example.easysublet.model.User;
import com.example.easysublet.repository.mainRepo;
import com.example.easysublet.repository.postRepo;

import java.util.List;

public class RoommatePostViewModel extends AndroidViewModel {

    private static final String TAG = "RoommatePostViewModel";
    private final MutableLiveData<RoommatePost> mPost;
    private MutableLiveData<List<RoommatePost>> mPostList;
    private postRepo repository;
    private List<RoommatePost> postList;
    private mainRepo accountRepo;
    private MutableLiveData<User> userMutableLiveData;

    public RoommatePostViewModel(Application application) {
        super(application);
        repository = new postRepo(application);
        mPost = repository.getOneRoommatePostData();
        mPostList = repository.getRoommatePostData();
        accountRepo = new mainRepo(application);
        userMutableLiveData = accountRepo.getMutableLiveData();
        Log.d(TAG, "RoommatePostViewModel() is called");
    }

    public MutableLiveData<RoommatePost> getPost() {
        return mPost;
    }
    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }
    public MutableLiveData<List<RoommatePost>> getmPostList(){return mPostList;}

    public void fectchUser(){
        accountRepo.getAccount("");
    }

    public void getPostList(){
        repository.getRoommatePostList();
        repository.getRoommatePostData().observe(getApplication(), new Observer<List<RoommatePost>>() {
            @Override
            public void onChanged(List<RoommatePost> roommatePosts) {
                postList = roommatePosts;
            }
        });
        //postList = repository.getHomePostData().getValue();
    }

    public void setPost(String index) {
//        mPostList.observe(getApplication(), new Observer<List<HomePost>>() {
//            @Override
//            public void onChanged(List<HomePost> homePosts) {
//                if (postList.size()>=index) {
//                    mPost.postValue(postList.get(index - 1));
//                }
//            }
//        });
        repository.getRoommatePost(index);
        Log.d("IMAGE ON 1 POST", "TEST Failed");
//        if (postList!= null && postList.size()>=index) {
//            Log.d("IMAGE ON 1 POST", "TEST IMAGE");
//            mPost.setValue(postList.get(index - 1));
//        }
    }

    public void uploadImage(){ //TODO: add parameter when testing ability to get image from user
//        Uri imageUri = Uri.parse("android.resource://" + getApplication().getPackageName() +
//                R.drawable.house2);
        Uri fileUri = Uri.parse("android.resource://com.example.easysublet/" + R.drawable.house2);
        repository.uploadImage(fileUri);
    }

}
