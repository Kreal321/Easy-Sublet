package com.example.easysublet.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easysublet.R;
import com.example.easysublet.databinding.ActivityHomeBinding;
import com.example.easysublet.viewmodel.HomeViewModel;

public class HomeActivity extends AppCompatActivity{

    private static final String TAG = "HomeActivity";

    private HomeViewModel homeViewModel;
    private ActivityHomeBinding binding;

    public static Intent newIntent(Context packageContext, String email){
        Intent intent = new Intent(packageContext, HomeActivity.class);
        intent.putExtra("email", email);
        return intent;
    }

    private void setResultCode(int code, String message){
        Intent intent = new Intent();
        intent.putExtra("message", message);
        setResult(code, intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() is called");

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        homeViewModel.setUser(getIntent().getStringExtra("email"));

        binding.welcome.append(" " + homeViewModel.getUser().getValue().getUsername());


        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = (Fragment) fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new PostFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        setResultCode(Activity.RESULT_OK, "Log out successfully");

        /** snip **/
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("ACTION_LOGOUT");
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG,"Logout in progress");
                finish();
            }
        }, intentFilter);
        //** snip **//
    }



    @Override
    protected void onStart() {
        super.onStart();
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