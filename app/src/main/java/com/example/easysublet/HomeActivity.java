package com.example.easysublet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private String user;
    private TextView mWelcome;

    public static Intent newIntent(Context packageContext, String userName){
        Intent intent = new Intent(packageContext, HomeActivity.class);
        intent.putExtra("userName", userName);
        return  intent;
    }

    private void setResultCode(int code, String message){
        Intent intent = new Intent();
        intent.putExtra("message", message);
        setResult(code, intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate() is called");

        user = getIntent().getStringExtra("userName");
        mWelcome = (TextView) findViewById(R.id.welcome);
        mWelcome.append(" " + user);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = (Fragment) fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new PostFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        setResultCode(Activity.RESULT_OK, "Log out successfully");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), "Invoked onStart()", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onStart() is called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() is called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() is called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() is called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() is called");


    }
}