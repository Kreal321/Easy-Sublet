package com.example.easysublet.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.easysublet.R;
import com.example.easysublet.databinding.ActivityEditHomePostBinding;
import com.example.easysublet.databinding.ActivityHomePostBinding;
import com.example.easysublet.model.HomePost;
import com.example.easysublet.viewmodel.HomePostViewModel;

public class EditHomePostActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "HomePostActivity";

    private HomePostViewModel homePostViewModel;
    private ActivityEditHomePostBinding binding;

    public static Intent newIntent(Context packageContext, String idx){
        Intent intent = new Intent(packageContext, EditHomePostActivity.class);
        intent.putExtra("idx", idx);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate() is called");

        homePostViewModel = new ViewModelProvider(this).get(HomePostViewModel.class);
        binding = ActivityEditHomePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        homePostViewModel.getPost().observe(this, new Observer<HomePost>() {
            @Override
            public void onChanged(HomePost homePost) {
                binding.titleEntry.setText(homePost.getTitle());
                binding.postPhoto.setImageResource(homePost.getImage());
                binding.addressEntry.setText(homePost.getAddress());
                binding.timeEntry.setText(homePost.getTime());
                binding.rentEntry.setText(Integer.toString(homePost.getRent()));
                binding.bedroomEntry.setText(Integer.toString(homePost.getBedroomNum()));
                binding.bathroomEntry.setText(Integer.toString(homePost.getBathroomNum()));
                binding.cbFurnished.setChecked(homePost.isFurnished());
                binding.cbPet.setChecked(homePost.getPet());
                binding.genderEntry.setText(homePost.getGender());
                binding.contactEntry.setText(homePost.getContact());
                binding.infoEntry.setText(homePost.getOther());
            }
        });

        homePostViewModel.setPost(getIntent().getStringExtra("idx"));

        binding.updateBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updateBtn:
                //TODO: need inputs checking

                break;

            default:
                break;
        }
    }
}