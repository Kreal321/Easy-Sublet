package com.example.easysublet.viewmodel;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.easysublet.model.RoommatePost;
import com.example.easysublet.repository.postRepo;

import java.util.List;

public class EditRoommatePostViewModel extends AndroidViewModel {

    private static final String TAG = "EditRoommatePostViewModel";
    private final MutableLiveData<RoommatePost> mPost;
    private MutableLiveData<List<RoommatePost>> mPostList;
    private postRepo repository;
    private List<RoommatePost> postList;
    private MutableLiveData<Uri> uriMutableLiveData;

    public EditRoommatePostViewModel(Application application) {
        super(application);
        repository = new postRepo(application);
        mPost = repository.getOneRoommatePostData();
        mPostList = repository.getRoommatePostData();
        uriMutableLiveData = repository.getUriMutableLiveData();
        Log.d(TAG, "EditRoommatePostViewModel() is called");
    }

    public MutableLiveData<RoommatePost> getPost() {
        return mPost;
    }
    public MutableLiveData<Uri> getUriMutableLiveData() {
        return uriMutableLiveData;
    }
    public MutableLiveData<List<RoommatePost>> getmPostList(){return mPostList;}

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
        repository.getRoommatePost(index);
        Log.d("IMAGE ON 1 POST", "TEST Failed");
    }

    public void updatePost(String index, String username, String title, boolean active,String address, String time, int rent, String contact, int bathroomNum, int bedroomNum, String gender, boolean pet, boolean furnished, String other, String image) {
        //TODO: add image and get Link
        RoommatePost newPost = new RoommatePost(index, username, image, title, active, address, contact, bathroomNum, bedroomNum, furnished, gender, pet, rent, time, other);
        Log.d(TAG, "update Home post: " + newPost.getIndex() + newPost.getUsername() + newPost.getImage() + newPost.getTitle() + newPost.isActive() + newPost.getAddress() + newPost.getContact() + newPost.getBathroomNum() + newPost.getBedroomNum() + newPost.isFurnished() + newPost.getGender() + newPost.getPet() + newPost.getRent() + newPost.getTime() + newPost.getOther());
        repository.updateRoommatePost(newPost);
    }

    //TODO: delete this after testing
    public void uploadImage(Uri imageUri){ //TODO: add parameter when testing ability to get image from user
//        Uri imageUri = Uri.parse("android.resource://" + getApplication().getPackageName() +
//                R.drawable.house2);
        //Uri fileUri = Uri.parse("android.resource://com.example.easysublet/" + R.drawable.house2);
        repository.uploadImage(imageUri);
    }

}
