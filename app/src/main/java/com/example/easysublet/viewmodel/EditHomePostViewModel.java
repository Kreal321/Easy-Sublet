package com.example.easysublet.viewmodel;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.easysublet.model.HomePost;
import com.example.easysublet.model.User;
import com.example.easysublet.repository.mainRepo;
import com.example.easysublet.repository.postRepo;

import java.util.List;

public class EditHomePostViewModel extends AndroidViewModel {

    private static final String TAG = "EditHomePostViewModel";
    private final MutableLiveData<HomePost> mPost;
    private MutableLiveData<User> userMutableLiveData;
    private MutableLiveData<List<HomePost>> mPostList;
    private mainRepo accountRepo;
    private postRepo repository;
    private List<HomePost> postList;

    public EditHomePostViewModel(Application application) {
        super(application);
        repository = new postRepo(application);
        accountRepo = new mainRepo(application);
        userMutableLiveData = accountRepo.getMutableLiveData();
        mPost = repository.getOneHomePostData();
        mPostList = repository.getHomePostData();
        Log.d(TAG, "EditHomePostViewModel() is called");
    }

    public MutableLiveData<HomePost> getPost() {
        return mPost;
    }
    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }
    public MutableLiveData<List<HomePost>> getmPostList(){return mPostList;}

    public void fectchUser(){
        accountRepo.getAccount("");
    }


    public void updatePost(String index, String username, String title, boolean active,String address, String time, int rent, String contact, int bathroomNum, int bedroomNum, String gender, boolean pet, boolean furnished, String other, String image) {
        //TODO: add image and get Link
        HomePost newPost = new HomePost(index, username, image, title, active, address, contact, bathroomNum, bedroomNum, furnished, gender, pet, rent, time, other);
        Log.d(TAG, "update Home post: " + newPost.getIndex() + newPost.getUsername() + newPost.getImage() + newPost.getTitle() + newPost.isActive() + newPost.getAddress() + newPost.getContact() + newPost.getBathroomNum() + newPost.getBedroomNum() + newPost.isFurnished() + newPost.getGender() + newPost.getPet() + newPost.getRent() + newPost.getTime() + newPost.getOther());
        repository.updateHomePost(newPost);
    }

    //TODO: delete this after testing
    public void uploadImage(Uri imageUri){ //TODO: add parameter when testing ability to get image from user
//        Uri imageUri = Uri.parse("android.resource://" + getApplication().getPackageName() +
//                R.drawable.house2);
        //Uri fileUri = Uri.parse("android.resource://com.example.easysublet/" + R.drawable.house2);
        repository.uploadImage(imageUri);
    }


}
