package com.example.easysublet.repository;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.easysublet.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class mainRepo {

    private Application application;
    private MutableLiveData<User> mutableLiveUserData;
    private MutableLiveData<Boolean> userLoggedData;
    private FirebaseDatabase mDatabase;
    private DatabaseReference myref;

    //NOTE: Firebase Authentication
    private FirebaseAuth auth;

    public mainRepo(Application application){
        this.application = application;
        mutableLiveUserData = new MutableLiveData<User>();
        userLoggedData = new MutableLiveData<>();
        mDatabase = FirebaseDatabase.getInstance();
        myref = mDatabase.getReference();

        //NOTE：Firebase Authentication
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null){
        }
    }

    public MutableLiveData<User> getMutableLiveData() {
        return mutableLiveUserData;
    }

    public MutableLiveData<Boolean> getUserLogData() {
        return userLoggedData;
    }

    public void signUp(String username,String email , String password){
//        User user = new User(username,email,password);
//        myref.child("User").child(email).setValue(user);

        //NOTE: Firebase Signup
        auth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    User user = new User(username,email,password);
                    mutableLiveUserData.postValue(user);

                    FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();

                    //NOTE: Create User-Post database
                    myref.child("User-Post").child(fuser.getUid()).child("roommate-post-count").setValue(0);
                    myref.child("User-Post").child(fuser.getUid()).child("home-post-count").setValue(0);

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(username)
                            .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                            .build();

                    fuser.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("updateDisplayName", "User profile updated.");
                                        Toast.makeText(application, "set username Failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    public void getAccount(String email){
//        myref.child("User").child(email).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    Log.e("firebase", "Error getting data", task.getException());
//                }
//                else {
//                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
//                    User user = task.getResult().getValue(User.class);
//                    mutableLiveUserData.postValue(user);
//                }
//            }
//        });

        SharedPreferences passStored = application.getBaseContext().getSharedPreferences("password",Context.MODE_PRIVATE);
        String password = passStored.getString("password",null);
        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        User user = new User(fuser.getDisplayName(), fuser.getEmail(), fuser.getUid());
        mutableLiveUserData.postValue(user);


    }

    public void login(String email , String pass){

        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SignInStatus", "signInWithEmail:success");
                    SharedPreferences shareUid = application.getSharedPreferences("uid",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = shareUid.edit();
                    editor.putString("uid",auth.getUid());
                    editor.apply();
                    editor.commit();//TODO: might not need this sharePreference later on.
                    User user = new User(auth.getCurrentUser().getDisplayName(),email,auth.getUid());
                    mutableLiveUserData.postValue(user);
                } else {
                    mutableLiveUserData.postValue(null);
                    // If sign in fails, display a message to the user.
                    Log.w("SignInStatus", "signInWithEmail:failure", task.getException());
                    Toast.makeText(application, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void changePassword(String oldPassword, String newPassword){
//        SharedPreferences emailStored = application.getSharedPreferences("email",Context.MODE_PRIVATE);
//        String email = emailStored.getString("email",null);
//        myref.child("User").child(email).child("password").setValue(newPassword);

        //DialogUtils.showProgressDialog(getActivity(), "Re-Authenticating", "Please wait...", false);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential authCredential = EmailAuthProvider.getCredential(firebaseUser.getEmail(), oldPassword);
        firebaseUser.reauthenticate(authCredential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            firebaseUser.updatePassword(newPassword)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("change password", "User password updated.");
                                            }
                                        }
                                    });
                            Toast.makeText(application, "Password Change Succeed.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(application, "Old password is not correct", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void updateEmail(String email){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseUser.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("updateEmail", "User email address updated.");
                    Toast.makeText(application, "Email Updated.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteAccount(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        //TODO: Delete associated post
        //Note: Remove user's post info
        myref.child("User-Post").child(uid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("deleteAccount-Post", "User account posts deleted.");
                    Toast.makeText(application, "User account posts deleted.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //TODO: delete related posts

//        myref.child("Home-Post").orderByChild("username").equalTo(uid).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                List<RoommatePost> roommatePostlist = new ArrayList<RoommatePost>();
//                if (dataSnapshot.exists()) {
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        roommatePostlist.add(snapshot.getValue(RoommatePost.class));
//                    }
//                }
//
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        myref.child("Roommate-Post").orderByChild("username").equalTo(uid).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Log.d("deleteAccount-Post", "User account posts deleted.");
//                    Toast.makeText(application, "User account posts deleted.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("deleteAccount", "User account deleted.");
                            Toast.makeText(application, "User account deleted.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        mutableLiveUserData.postValue(null);

    }

    public void logOut(){
        auth.signOut();
        userLoggedData.postValue(true);
    }

    public void cleanData(){
        mutableLiveUserData = new MutableLiveData<User>();
        userLoggedData = new MutableLiveData<>();
    }


}
