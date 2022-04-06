package com.example.easysublet.repository;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.easysublet.R;
import com.example.easysublet.model.HomePost;
import com.example.easysublet.model.RoommatePost;
import com.example.easysublet.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class mainRepo {

    private Application application;
    private MutableLiveData<User> mutableLiveUserData;
    private MutableLiveData<Boolean> userLoggedData;
    private FirebaseDatabase mDatabase;
    private DatabaseReference myref;

    public mainRepo(Application application){
        this.application = application;
        mutableLiveUserData = new MutableLiveData<User>();
        userLoggedData = new MutableLiveData<>();
        mDatabase = FirebaseDatabase.getInstance();
        myref = mDatabase.getReference();
    }

    public MutableLiveData<User> getMutableLiveData() {
        return mutableLiveUserData;
    }

    public MutableLiveData<Boolean> getUserLogData() {
        return userLoggedData;
    }

    public void signUp(String username,String email , String password){
        User user = new User(username,email,password);
        myref.child("User").child(email).setValue(user);
    }

    public void getAccount(String email){
        myref.child("User").child(email).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    User user = task.getResult().getValue(User.class);
                    mutableLiveUserData.postValue(user);
                }
            }
        });
    }

    public void login(String email , String pass){

        myref.child("User").child(email).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    User user = task.getResult().getValue(User.class);
                    Log.d("Login",String.valueOf(task.getResult().getValue()));
                    if(user == null || !email.equals(user.getEmail())||!user.passwordIsCorrect(pass)) {
                        user = null;
                    }else {
                        SharedPreferences sharedPref = application.getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("email", email);
                        editor.putString("username", "testuser");
                        editor.apply();
                        editor.commit();
                    }
                    mutableLiveUserData.postValue(user);
                }
            }
        });

    }

    public void changePassword(String newPassword){
        SharedPreferences emailStored = application.getSharedPreferences("email",Context.MODE_PRIVATE);
        String email = emailStored.getString("email",null);
        myref.child("User").child(email).child("password").setValue(newPassword);
    }

    public void deleteAccount(){
        SharedPreferences emailStored = application.getSharedPreferences("email",Context.MODE_PRIVATE);
        String email = emailStored.getString("email",null);
        myref.child("User").child(email).removeValue();
        Log.d("deleteAccount", "User account deleted.");
    }

    public void logOut(){
        userLoggedData.postValue(true);
    }


    //TODO: implement

    public static List<HomePost> getHomePostList() {
        List<HomePost> list = new ArrayList<>();
        list.add(new HomePost("1", "Walter", R.drawable.apart1, "This is post 1", true,"Jean Baptiste Point du Sable Lake Shore Drive", "walter@hotmail.com", 2,2, true, "no preference", true, 800, "2022-03-21", "One of my roommate feed a cat."));
        list.add(new HomePost("2","Sam", R.drawable.apart2, "This is post 2", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new HomePost("3", "testuser", R.drawable.house1, "This is post 3", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new HomePost("4", "Test user", R.drawable.house2, "This is post 4", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new HomePost("5","Test user", R.drawable.house3, "This is post 5", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new HomePost("6","testuser", R.drawable.apart1, "This is post 6", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new HomePost("7","Test user", R.drawable.apart2, "This is post 7", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new HomePost("8","Test user", R.drawable.house1, "This is post 8", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new HomePost("9","Test user", R.drawable.house2, "This is post 9", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new HomePost("10","Test user", R.drawable.house3, "This is post 10", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new HomePost("11","Test user", R.drawable.apart1, "This is post 11", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new HomePost("12","Test user", R.drawable.apart2, "This is post 12", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new HomePost("13","Test user", R.drawable.house1, "This is post 13", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new HomePost("14","Test user", R.drawable.house2, "This is post 14", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new HomePost("15","Test user", R.drawable.house3, "This is post 15", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        return list;
    }

    public static HomePost getHomePost(String idx) {
        List<HomePost> list = getHomePostList();
        return list.stream().filter(HomePost -> idx.equals(HomePost.getIndex())).findFirst().orElse(null);
    }

    public static List<RoommatePost> getRoommatePostList() {
        List<RoommatePost> list = new ArrayList<>();
        list.add(new RoommatePost("1", "Test user", R.drawable.apart1, "This is post 1", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("2","Test user", R.drawable.apart2, "This is post 2", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("3", "Test user", R.drawable.house1, "This is post 3", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("5","Test user", R.drawable.house3, "This is post 5", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("4", "Test user", R.drawable.house2, "This is post 4", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("6","testuser", R.drawable.house1, "This is post 6", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("7","Test user", R.drawable.apart2, "This is post 7", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("8","Test user", R.drawable.apart1, "This is post 8", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("9","Test user", R.drawable.house2, "This is post 9", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("10","testuser", R.drawable.house3, "This is post 10", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("11","Test user", R.drawable.apart1, "This is post 11", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("12","Test user", R.drawable.apart2, "This is post 12", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("13","Test user", R.drawable.house1, "This is post 13", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("14","Test user", R.drawable.house2, "This is post 14", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("15","Test user", R.drawable.house3, "This is post 15", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        return list;
    }

    public static RoommatePost getRoommatePost(String idx) {
        List<RoommatePost> list = getRoommatePostList();
        return list.stream().filter(RoommatePost -> idx.equals(RoommatePost.getIndex())).findFirst().orElse(null);
    }

    public static List<HomePost> getSearchedHomePostList(String title) {
        List<HomePost> list = getHomePostList();
        if(title.length() == 0) return list;
        return list.stream().filter(HomePost -> HomePost.getTitle().contains(title)).collect(Collectors.toList());
    }

    public static List<RoommatePost> getSearchedRoommatePostList(String title) {
        List<RoommatePost> list = getRoommatePostList();
        if(title.length() == 0) return list;
        return list.stream().filter(RoommatePost -> RoommatePost.getTitle().contains(title)).collect(Collectors.toList());
    }

    public static List<HomePost> getMyHomePostList(String username) {
        List<HomePost> list = getHomePostList();
        return list.stream().filter(HomePost -> HomePost.getUsername().equals(username)).collect(Collectors.toList());
    }

    public static List<RoommatePost> getMyRoommatePostList(String username) {
        List<RoommatePost> list = getRoommatePostList();
        return list.stream().filter(RoommatePost -> RoommatePost.getUsername().equals(username)).collect(Collectors.toList());
    }

}
