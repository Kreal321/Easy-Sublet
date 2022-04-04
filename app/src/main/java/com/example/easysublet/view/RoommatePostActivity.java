package com.example.easysublet.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.easysublet.databinding.ActivityRoommatePostBinding;
import com.example.easysublet.model.RoommatePost;
import com.example.easysublet.viewmodel.RoommatePostViewModel;

public class RoommatePostActivity extends AppCompatActivity {

    private static final String TAG = "RoommatePostActivity";

    private RoommatePostViewModel roommatePostViewModel;
    private ActivityRoommatePostBinding binding;

    public static Intent newIntent(Context packageContext, String idx){
        Intent intent = new Intent(packageContext, RoommatePostActivity.class);
        intent.putExtra("idx", idx);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate() is called");

        roommatePostViewModel = new ViewModelProvider(this).get(RoommatePostViewModel.class);
        binding = ActivityRoommatePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        roommatePostViewModel.getPost().observe(this, new Observer<RoommatePost>() {
            @Override
            public void onChanged(RoommatePost roommatePost) {
                binding.title.setText(roommatePost.getTitle());
                binding.image.setImageResource(roommatePost.getImage());
            }
        });

        roommatePostViewModel.setPost(getIntent().getStringExtra("idx"));

    }
}