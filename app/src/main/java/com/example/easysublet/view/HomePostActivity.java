package com.example.easysublet.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.easysublet.databinding.ActivityHomePostBinding;
import com.example.easysublet.model.HomePost;
import com.example.easysublet.model.RoommatePost;
import com.example.easysublet.viewmodel.HomePostViewModel;

public class HomePostActivity extends AppCompatActivity {

    private static final String TAG = "HomePostActivity";

    private HomePostViewModel homePostViewModel;
    private ActivityHomePostBinding binding;

    public static Intent newIntent(Context packageContext, int idx){
        Intent intent = new Intent(packageContext, HomePostActivity.class);
        intent.putExtra("idx", idx);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate() is called");

        homePostViewModel = new ViewModelProvider(this).get(HomePostViewModel.class);
        binding = ActivityHomePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        homePostViewModel.getPost().observe(this, new Observer<HomePost>() {
            @Override
            public void onChanged(HomePost homePost) {
                binding.title.setText(homePost.getTitle());
                binding.image.setImageResource(homePost.getImage());
            }
        });

        homePostViewModel.setPost(getIntent().getIntExtra("idx", 0));

    }
}