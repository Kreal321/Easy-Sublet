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
                        SharedPreferences sharedPref = application.getSharedPreferences("email",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("email",email);
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
        list.add(new HomePost("2","Walter", R.drawable.apart2, "This is post 2", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new HomePost("3", "Walter", R.drawable.house1, "This is post 3", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new HomePost("4", "Walter", R.drawable.house2, "This is post 4", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new HomePost("5","Walter", R.drawable.house3, "This is post 5", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new HomePost("6","Walter", R.drawable.apart1, "This is post 6", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new HomePost("7","Walter", R.drawable.apart2, "This is post 7", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new HomePost("8","Walter", R.drawable.house1, "This is post 8", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new HomePost("9","Walter", R.drawable.house2, "This is post 9", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new HomePost("10","Walter", R.drawable.house3, "This is post 10", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new HomePost("11","Walter", R.drawable.apart1, "This is post 11", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new HomePost("12","Walter", R.drawable.apart2, "This is post 12", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new HomePost("13","Walter", R.drawable.house1, "This is post 13", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new HomePost("14","Walter", R.drawable.house2, "This is post 14", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new HomePost("15","Walter", R.drawable.house3, "This is post 15", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        return list;
    }

    public static HomePost getHomePost(String idx) {
        List<HomePost> list = getHomePostList();
        return list.stream().filter(HomePost -> idx.equals(HomePost.getIndex())).findFirst().orElse(null);
    }

    public static List<RoommatePost> getRoommatePostList() {
        List<RoommatePost> list = new ArrayList<>();
        list.add(new RoommatePost("1", "Walter", R.drawable.apart1, "This is post 1", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("2","Walter", R.drawable.apart2, "This is post 2", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("3", "Walter", R.drawable.house1, "This is post 3", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("5","Walter", R.drawable.house3, "This is post 5", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("4", "Walter", R.drawable.house2, "This is post 4", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("6","Walter", R.drawable.apart1, "This is post 6", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("7","Walter", R.drawable.apart2, "This is post 7", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("8","Walter", R.drawable.house1, "This is post 8", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("9","Walter", R.drawable.house2, "This is post 9", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("10","Walter", R.drawable.house3, "This is post 10", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("11","Walter", R.drawable.apart1, "This is post 11", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("12","Walter", R.drawable.apart2, "This is post 12", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("13","Walter", R.drawable.house1, "This is post 13", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("14","Walter", R.drawable.house2, "This is post 14", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        list.add(new RoommatePost("15","Walter", R.drawable.house3, "This is post 15", true,"N High Str", "walter@hotmail.com", 2,2, true, "male", true, 800, "2022-03-21", ""));
        return list;
    }

    public static RoommatePost getRoommatePost(String idx) {
        List<RoommatePost> list = getRoommatePostList();
        return list.stream().filter(RoommatePost -> idx.equals(RoommatePost.getIndex())).findFirst().orElse(null);
    }

}
