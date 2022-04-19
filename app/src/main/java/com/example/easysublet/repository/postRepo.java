package com.example.easysublet.repository;

import android.app.Application;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.easysublet.model.HomePost;
import com.example.easysublet.model.RoommatePost;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class postRepo {

    private Application application;
    private FirebaseDatabase mDatabase;
    private DatabaseReference myref;
    private MutableLiveData<List<HomePost>> mutableLiveHomePosts;
    private MutableLiveData<HomePost> mutableLiveSingleHomePost;
    private MutableLiveData<List<RoommatePost>> mutableLiveRoommatePosts;
    private MutableLiveData<RoommatePost> mutableLiveSingleRoommatePost;
    private MutableLiveData<Uri> uriMutableLiveData;




    public postRepo(Application application){
        this.application = application;
        mutableLiveHomePosts = new MutableLiveData<List<HomePost>>();
        mutableLiveRoommatePosts = new MutableLiveData<List<RoommatePost>>();
        mDatabase = FirebaseDatabase.getInstance();
        myref = mDatabase.getReference();

        //For single post
        mutableLiveSingleHomePost = new MutableLiveData<HomePost>();
        mutableLiveSingleRoommatePost = new MutableLiveData<RoommatePost>();
        uriMutableLiveData = new MutableLiveData<Uri>();
    }

    public MutableLiveData<List<HomePost>> getHomePostData() { return mutableLiveHomePosts; }
    public MutableLiveData<HomePost> getOneHomePostData() { return mutableLiveSingleHomePost; }
    public MutableLiveData<List<RoommatePost>> getRoommatePostData() { return mutableLiveRoommatePosts;}
    public MutableLiveData<RoommatePost> getOneRoommatePostData() { return mutableLiveSingleRoommatePost; }
    public MutableLiveData<Uri> getUriMutableLiveData() { return uriMutableLiveData; }


    //TODO: implement

    public void addHomePost(HomePost homePost){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();
        // Generate a reference to a new location and add some data using push()
        DatabaseReference newPostRef = myref.child("Home-Post").push();
        // Get the unique ID generated by push() by accessing its key
        String postID = newPostRef.getKey();
        homePost.setIndex(postID);
        myref.child("Home-Post").child(postID).setValue(homePost);

        //add count for this user
        myref.child("User-Post").child(uid).child("home-post-count").setValue(ServerValue.increment(1));

    }

    public void addRoommatePost(RoommatePost roommatePost){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();

        // Generate a reference to a new location and add some data using push()
        DatabaseReference newPostRef = myref.child("Roommate-Post").push();
        // Get the unique ID generated by push() by accessing its key
        String postID = newPostRef.getKey();
        roommatePost.setIndex(postID);
        myref.child("Roommate-Post").child(postID).setValue(roommatePost);

        //add count for this user
        myref.child("User-Post").child(uid).child("roommate-post-count").setValue(ServerValue.increment(1));

    }


    public void updateHomePost(HomePost homePost){
        String postID = homePost.getIndex();
        myref.child("Home-Post").child(postID).setValue(homePost).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Codes for successful!
                Toast.makeText(application,"updated HomePost!",Toast.LENGTH_LONG).show();
            }
        });;
    }

    public void updateRoommatePost(RoommatePost roommatePost){
        String postID = roommatePost.getIndex();
        myref.child("Roommate-Post").child(postID).setValue(roommatePost).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Codes for successful!
                Toast.makeText(application,"updated RoommatePost!",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void getHomePostList() {
        for(int i = 0; i<2;i++) {
            myref.child("Home-Post").orderByChild("rent").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        List<HomePost> newHomeList = new ArrayList<HomePost>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            newHomeList.add(snapshot.getValue(HomePost.class));
                        }
                        mutableLiveHomePosts.postValue(newHomeList);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }


    public void getHomePost(String index) {

        myref.child("Home-Post").child(index).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    HomePost post = dataSnapshot.getValue(HomePost.class);
                    mutableLiveSingleHomePost.postValue(post);
                }
                //mutableLiveSingleHomePost.postValue(post);
                //TODO: get only one child from firebase
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getRoommatePostList() {
        for(int i = 0; i<2;i++) {
            myref.child("Roommate-Post").orderByChild("rent").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<RoommatePost> roommatePostlist = new ArrayList<RoommatePost>();
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            roommatePostlist.add(snapshot.getValue(RoommatePost.class));
                        }
                    }
                    mutableLiveRoommatePosts.postValue(roommatePostlist);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public void getRoommatePost(String index) {

        myref.child("Roommate-Post").child(index).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                RoommatePost roommatePost = new RoommatePost();
                if (dataSnapshot.exists()) {
                    roommatePost=dataSnapshot.getValue(RoommatePost.class);
                }
                mutableLiveSingleRoommatePost.postValue(roommatePost);

                //TODO: get only one child from firebase
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void uploadImage(Uri imagePath){
        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        StorageReference riversRef = storageRef.child("images/"+imagePath.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(imagePath);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(application,"Upload failed!",Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Toast.makeText(application,"Upload succeed!",Toast.LENGTH_LONG).show();
            }
        });

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return riversRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    uriMutableLiveData.postValue(task.getResult());
                    Toast.makeText(application,"Image Link Get!",Toast.LENGTH_LONG).show();
                } else {
                    // Handle failures
                    // ...
                    Toast.makeText(application,"Image Link Not Get!",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //Other functions
    public void getSearchedHomePostList(String title) {

        myref.child("Home-Post").orderByChild("title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<HomePost> newHomeList = new ArrayList<HomePost>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        HomePost post = snapshot.getValue(HomePost.class);
                        String pTitle = post.getTitle().toLowerCase();
                        if (pTitle.contains(title.toLowerCase())){
                            newHomeList.add(post);
                        }

                    }
                }
                mutableLiveHomePosts.postValue(newHomeList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getSearchedRoommatePostList(String title) {

        myref.child("Roommate-Post").orderByChild("title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<RoommatePost> roommatePostlist = new ArrayList<RoommatePost>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        RoommatePost post = snapshot.getValue(RoommatePost.class);
                        String pTitle = post.getTitle().toLowerCase();
                        if (pTitle.contains(title.toLowerCase())){
                            roommatePostlist.add(post);
                        }

                    }
                }
                mutableLiveRoommatePosts.postValue(roommatePostlist);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getMyHomePostList(String username) {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        myref.child("Home-Post").orderByChild("username").equalTo(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<HomePost> newHomeList = new ArrayList<HomePost>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        newHomeList.add(snapshot.getValue(HomePost.class));
                    }
                }
                mutableLiveHomePosts.postValue(newHomeList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getMyRoommatePostList(String username) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        myref.child("Roommate-Post").orderByChild("username").equalTo(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<RoommatePost> roommatePostlist = new ArrayList<RoommatePost>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        roommatePostlist.add(snapshot.getValue(RoommatePost.class));
                    }
                }
                mutableLiveRoommatePosts.postValue(roommatePostlist);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void cleanData(){
        mutableLiveHomePosts = new MutableLiveData<List<HomePost>>();
        mutableLiveRoommatePosts = new MutableLiveData<List<RoommatePost>>();
        mutableLiveSingleHomePost = new MutableLiveData<HomePost>();
        mutableLiveSingleRoommatePost = new MutableLiveData<RoommatePost>();
    }


}
