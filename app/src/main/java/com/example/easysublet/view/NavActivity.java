package com.example.easysublet.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.easysublet.R;
import com.example.easysublet.databinding.ActivityNavBinding;
import com.example.easysublet.model.User;
import com.example.easysublet.viewmodel.NavViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class NavActivity extends AppCompatActivity {

    private static final String TAG = "NavActivity";

    private NavViewModel navViewModel;
    private ActivityNavBinding binding;

    private User user;

    public static Intent newIntent(Context packageContext, User user){
        Intent intent = new Intent(packageContext, NavActivity.class);
        intent.putExtra("User", user);
        return intent;
    }

    private void setResultCode(int code, String message){
        Intent intent = new Intent();
        intent.putExtra("message", message);
        setResult(code, intent);
    }

    public static void setLanguage(Activity activity) {
        SharedPreferences user = activity.getSharedPreferences("user" , Context.MODE_PRIVATE);
        String language = user.getString("language", "en");

        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() is called");

        setLanguage(NavActivity.this);
        binding = ActivityNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navViewModel =  new ViewModelProvider(this).get(NavViewModel.class);

        //TODO: need to check that this should pass to next level
        //user = (User) getIntent().getSerializableExtra("User");
        //String uid = user.getUid();

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

        //TODO: 我要怎么把User Object传向下一个 fragment or activity????: 暂时还是每个activity都query一次，获得新的user，之后再优化这部分
        //TODO: addHomePost 和addRoomPost 没做！！
    }

    @Override
    public void finish() {
        Log.d(TAG, "finish() is called");
        Intent data = new Intent();
        setResult(RESULT_OK, data);

        super.finish();
    }
}