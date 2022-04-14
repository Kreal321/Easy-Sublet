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

public class AddHomePostViewModel extends AndroidViewModel {

    private static final String TAG = "AddPostViewModel";
    private final MutableLiveData<HomePost> post;
    private MutableLiveData<Uri> uriMutableLiveData;
    private MutableLiveData<User> userMutableLiveData;
    private postRepo repo;
    private mainRepo accountRepo;

    public AddHomePostViewModel(Application application) {
        super(application);
        post = new MutableLiveData<>();
        accountRepo = new mainRepo(application);
        repo = new postRepo(application);
        uriMutableLiveData = repo.getUriMutableLiveData();
        userMutableLiveData = accountRepo.getMutableLiveData();
        Log.d(TAG, "AddHomePostViewModel() is called");
    }

    public MutableLiveData<HomePost> getPost() {
        return this.post;
    }
    public MutableLiveData<Uri> getUploadedUri() {
        return this.uriMutableLiveData;
    }
    public MutableLiveData<User> getUserMutableLiveData() {
        return this.userMutableLiveData;
    }

    // TODO: link this to repository
    public void createPost(String username, String title, String address, String time, int rent, String contact, int bathroomNum, int bedroomNum, String gender, boolean pet, boolean furnished, String other, String image) {
        String index = "autoFilled";
        boolean active = true;
        //TODO: add image and get Link
        HomePost newPost = new HomePost(index, username, image, title, active, address, contact, bathroomNum, bedroomNum, furnished, gender, pet, rent, time, other);
//        post.setValue(newPost);
        Log.d(TAG, "createPost: " + newPost.getIndex() + newPost.getUsername() + newPost.getImage() + newPost.getTitle() + newPost.isActive() + newPost.getAddress() + newPost.getContact() + newPost.getBathroomNum() + newPost.getBedroomNum() + newPost.isFurnished() + newPost.getGender() + newPost.getPet() + newPost.getRent() + newPost.getTime() + newPost.getOther());
        repo.addHomePost(newPost);
    }

    public void fetchUser(){
        accountRepo.getAccount("");
    }

    //TODO: delete this after testing
    public void uploadImage(Uri imageUri){ //TODO: add parameter when testing ability to get image from user
//        Uri imageUri = Uri.parse("android.resource://" + getApplication().getPackageName() +
//                R.drawable.house2);
        //Uri fileUri = Uri.parse("android.resource://com.example.easysublet/" + R.drawable.house2);
        repo.uploadImage(imageUri);
    }

}