package com.example.easysublet.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysublet.model.HomePost;

public class AddHomePostViewModel extends ViewModel {

    private static final String TAG = "AddPostViewModel";
    private final MutableLiveData<HomePost> post;

    public AddHomePostViewModel() {
        post = new MutableLiveData<>();
        Log.d(TAG, "AddHomePostViewModel() is called");
    }

    public LiveData<HomePost> getPost() {
        return this.post;
    }

    // TODO: link this to repository
    public void createPost(String title, String address, String time, int rent, String contact, int bathroomNum, int bedroomNum, String gender, boolean pet, boolean furnished, String other, int image) {
        String index = "autoFilled";
        String username = "testuser";
        boolean active = true;
        HomePost newPost = new HomePost(index, username, image, title, active, address, contact, bathroomNum, bedroomNum, furnished, gender, pet, rent, time, other);
        post.setValue(newPost);
        Log.d(TAG, "createPost: " + newPost.getIndex() + newPost.getUsername() + newPost.getImage() + newPost.getTitle() + newPost.isActive() + newPost.getAddress() + newPost.getContact() + newPost.getBathroomNum() + newPost.getBedroomNum() + newPost.isFurnished() + newPost.getGender() + newPost.getPet() + newPost.getRent() + newPost.getTime() + newPost.getOther());

    }

}