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

    //Helper function
    //My top posts by number of stars
//    public void addCount(String userID){
//        myref.child("User").child(userID).child("postCount").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    Log.e("updateCount", "Error getting data", task.getException());
//                }
//                else {
//                    Log.d("updateCount", String.valueOf(task.getResult().getValue()));
//                    myref.child("User").child(userID).child("postCount").setValue(Integer.parseInt(String.valueOf(task.getResult().getValue()))+1);
//                }
//            }
//        });
//    }
    //---


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

        //add count for this user
        //addCount(uid);
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

                  //Note: For Test Only
//                List<HomePost> newHomeList = new ArrayList<>();
//                newHomeList.add(new HomePost("postkey1", "hZmlZQm00EYba4JqSQyavfXosQH3", "https://firebasestorage.googleapis.com/v0/b/easysublet-d9441.appspot.com/o/images%2Fapart1.jpeg?alt=media&token=87f1ac1a-66ec-4daf-ac13-08ba2b850c71", "This is post 1", true,"1009 N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-23", ""));
//                mutableLiveHomePosts.postValue(newHomeList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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

//    public static HomePost getHomePost(int idx) {
//        List<HomePost> list = mainRepo.getHomePostData().getValue();
//        return list.get(idx-1);
//    }

    public void getRoommatePostList() {

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

//                List<RoommatePost> roommatePostlist = new ArrayList<>();
//                roommatePostlist.add(new RoommatePost("1","ban", R.drawable.apart1, "This is test", true,"N High Str 1999", "walter@hotmail.com", 2,2, true, "male", false, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("2","Walter", R.drawable.apart2, "This is post 2", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("3","Walter", R.drawable.house1, "This is post 3", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", false, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("4","Walter", R.drawable.house2, "This is post 4", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("5","Walter", R.drawable.house3, "This is post 5", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", false, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("6","Walter", R.drawable.apart1, "This is post 6", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("7","Walter", R.drawable.apart2, "This is post 7", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", false, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("8","Walter", R.drawable.house1, "This is post 8", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", false, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("9","Walter", R.drawable.house2, "This is post 9", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("10","Walter", R.drawable.house3, "This is post 10", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", false, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("11","Walter", R.drawable.apart1, "This is post 11", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("12","Walter", R.drawable.apart2, "This is post 12", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", false, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("13","Walter", R.drawable.house1, "This is post 13", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("14","Walter", R.drawable.house2, "This is post 14", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", false, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("15","Walter", R.drawable.house3, "This is post 15", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));


//                mutableLiveRoommatePosts.postValue(roommatePostlist);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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

//                List<RoommatePost> roommatePostlist = new ArrayList<>();
//                roommatePostlist.add(new RoommatePost("1","ban", R.drawable.apart1, "This is test", true,"N High Str 1999", "walter@hotmail.com", 2,2, true, "male", false, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("2","Walter", R.drawable.apart2, "This is post 2", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("3","Walter", R.drawable.house1, "This is post 3", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", false, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("4","Walter", R.drawable.house2, "This is post 4", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("5","Walter", R.drawable.house3, "This is post 5", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", false, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("6","Walter", R.drawable.apart1, "This is post 6", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("7","Walter", R.drawable.apart2, "This is post 7", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", false, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("8","Walter", R.drawable.house1, "This is post 8", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", false, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("9","Walter", R.drawable.house2, "This is post 9", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("10","Walter", R.drawable.house3, "This is post 10", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", false, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("11","Walter", R.drawable.apart1, "This is post 11", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("12","Walter", R.drawable.apart2, "This is post 12", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", false, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("13","Walter", R.drawable.house1, "This is post 13", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("14","Walter", R.drawable.house2, "This is post 14", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", false, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("15","Walter", R.drawable.house3, "This is post 15", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
//
//                mutableLiveSingleRoommatePost.postValue(roommatePostlist.get(3));
                //TODO: get only one child from firebase
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


//    public static RoommatePost getRoommatePost(int idx) {
//        List<RoommatePost> list = getRoommatePostList();
//        return list.get(idx-1);
//    }

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

    //TODO: implement-some are new added from github- 4-12

    //Other functions
    public void getSearchedHomePostList(String title) {

        myref.child("Home-Post").orderByChild("title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<HomePost> newHomeList = new ArrayList<HomePost>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        HomePost post = snapshot.getValue(HomePost.class);
                        if (post.getTitle().contains(title)){
                            newHomeList.add(post);
                        }

                    }
                }
                mutableLiveHomePosts.postValue(newHomeList);

//                List<HomePost> newHomeList = new ArrayList<>();
//                newHomeList.add(new HomePost("1", "jack", R.drawable.apart1, "This is post 1", true,"1009 N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
//                newHomeList.add(new HomePost("2","Walter", R.drawable.apart2, "This is post 2", true,"2001 N High Str", "walter@hotmail.com", 2,2, false, "male", false, 800, "2022-03-21", ""));
//                newHomeList.add(new HomePost("3", "Walter", R.drawable.house1, "This is post 3", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", false, 800, "2022-03-21", ""));
//                newHomeList.add(new HomePost("4", "Walter", R.drawable.house2, "This is post 4", true,"N High Str", "walter@hotmail.com", 2,2, false, "male", false, 800, "2022-03-21", ""));
//                mutableLiveHomePosts.postValue(newHomeList);
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
                        if (post.getTitle().contains(title)){
                            roommatePostlist.add(post);
                        }

                    }
                }
                mutableLiveRoommatePosts.postValue(roommatePostlist);

//                List<RoommatePost> roommatePostlist = new ArrayList<>();
//                roommatePostlist.add(new RoommatePost("1","ban", R.drawable.apart1, "This is test", true,"N High Str 1999", "walter@hotmail.com", 2,2, true, "male", false, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("2","Walter", R.drawable.apart2, "This is post 2", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("3","Walter", R.drawable.house1, "This is post 3", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", false, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("4","Walter", R.drawable.house2, "This is post 4", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("5","Walter", R.drawable.house3, "This is post 5", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", false, 800, "2022-03-21", ""));
//                roommatePostlist.add(new RoommatePost("6","Walter", R.drawable.apart1, "This is post 6", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
//
//                mutableLiveRoommatePosts.postValue(roommatePostlist);
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

//                List<HomePost> newHomeList = new ArrayList<>();
//                newHomeList.add(new HomePost("1", "jack", R.drawable.apart1, "This is post 1", true,"1009 N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
//                newHomeList.add(new HomePost("2","Walter", R.drawable.apart2, "This is post 2", true,"2001 N High Str", "walter@hotmail.com", 2,2, false, "male", false, 800, "2022-03-21", ""));
//                mutableLiveHomePosts.postValue(newHomeList);
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

//                List<RoommatePost> roommatePostlist = new ArrayList<>();
//                roommatePostlist.add(new RoommatePost("1","ban", R.drawable.apart1, "This is test", true,"N High Str 1999", "walter@hotmail.com", 2,2, true, "male", false, 800, "2022-03-21", ""));
//
//                mutableLiveRoommatePosts.postValue(roommatePostlist);
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
