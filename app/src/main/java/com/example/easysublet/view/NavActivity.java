package com.example.easysublet.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.easysublet.R;
import com.example.easysublet.viewmodel.NavViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.easysublet.databinding.ActivityNavBinding;

public class NavActivity extends AppCompatActivity {

    private static final String TAG = "NavActivity";

    private NavViewModel navViewModel;
    private ActivityNavBinding binding;


//    public static Intent newIntent(Context packageContext, String email){
//        Intent intent = new Intent(packageContext, HomeActivity.class);
//        intent.putExtra("email", email);
//        return intent;
//    }

    private void setResultCode(int code, String message){
        Intent intent = new Intent();
        intent.putExtra("message", message);
        setResult(code, intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() is called");

        binding = ActivityNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navViewModel =  new ViewModelProvider(this).get(NavViewModel.class);

        SharedPreferences emailStored = getSharedPreferences("email",Context.MODE_PRIVATE);
        String email = emailStored.getString("email",null);
        navViewModel.setUser(email);

        BottomNavigationView navView = findViewById(R.id.nav_view);

//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_find_home, R.id.navigation_find_roommates, R.id.navigation_profile)
//                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_nav);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}