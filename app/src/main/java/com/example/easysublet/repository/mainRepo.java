package com.example.easysublet.repository;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.easysublet.R;
import com.example.easysublet.model.Post;
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

    public static List<Post> getList() {
        List<Post> list = new ArrayList<>();
        list.add(new Post("This is post 1", R.drawable.apart1, 1));
        list.add(new Post("This is post 2",R.drawable.apart2, 2));
        list.add(new Post("This is post 3",R.drawable.house1, 3));
        list.add(new Post("This is post 4",R.drawable.house2, 4));
        list.add(new Post("This is post 5",R.drawable.house3, 5));
        list.add(new Post("This is post 5",R.drawable.house3, 5));
        list.add(new Post("This is post 6", R.drawable.apart1, 6));
        list.add(new Post("This is post 7",R.drawable.apart2, 7));
        list.add(new Post("This is post 8",R.drawable.house1, 8));
        list.add(new Post("This is post 9",R.drawable.house2, 9));
        list.add(new Post("This is post 10",R.drawable.house3, 10));
        list.add(new Post("This is post 11", R.drawable.apart1, 11));
        list.add(new Post("This is post 12",R.drawable.apart2, 12));
        list.add(new Post("This is post 13",R.drawable.house1, 13));
        list.add(new Post("This is post 14",R.drawable.house2, 14));
        list.add(new Post("This is post 15",R.drawable.house3, 15));
        return list;
    }

    public static Post getPost(int idx) {
        List<Post> list = getList();
        return list.get(idx);
    }

}
