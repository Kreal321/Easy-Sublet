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
import com.example.easysublet.databinding.ActivityEditRoommatePostBinding;
import com.example.easysublet.databinding.ActivityRoommatePostBinding;
import com.example.easysublet.model.HomePost;
import com.example.easysublet.model.RoommatePost;
import com.example.easysublet.viewmodel.RoommatePostViewModel;

public class EditRoommatePostActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "RoommatePostActivity";

    private RoommatePostViewModel roommatePostViewModel;
    private ActivityEditRoommatePostBinding binding;

    public static Intent newIntent(Context packageContext, String idx){
        Intent intent = new Intent(packageContext, EditRoommatePostActivity.class);
        intent.putExtra("idx", idx);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate() is called");

        roommatePostViewModel = new ViewModelProvider(this).get(RoommatePostViewModel.class);
        binding = ActivityEditRoommatePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        roommatePostViewModel.getPost().observe(this, new Observer<RoommatePost>() {
            @Override
            public void onChanged(RoommatePost roommatePost) {
                binding.titleEntry.setText(roommatePost.getTitle());
                binding.postPhoto.setImageResource(roommatePost.getImage());
                binding.addressEntry.setText(roommatePost.getAddress());
                binding.timeEntry.setText(roommatePost.getTime());
                binding.rentEntry.setText(Integer.toString(roommatePost.getRent()));
                binding.bedroomEntry.setText(Integer.toString(roommatePost.getBedroomNum()));
                binding.bathroomEntry.setText(Integer.toString(roommatePost.getBathroomNum()));
                binding.cbFurnished.setChecked(roommatePost.isFurnished());
                binding.cbPet.setChecked(roommatePost.getPet());
                binding.genderEntry.setText(roommatePost.getGender());
                binding.contactEntry.setText(roommatePost.getContact());
                binding.infoEntry.setText(roommatePost.getOther());
            }
        });

        roommatePostViewModel.setPost(getIntent().getStringExtra("idx"));

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