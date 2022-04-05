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
import com.example.easysublet.databinding.ActivityRoommatePostBinding;
import com.example.easysublet.model.RoommatePost;
import com.example.easysublet.viewmodel.RoommatePostViewModel;

public class RoommatePostActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "RoommatePostActivity";

    private RoommatePostViewModel roommatePostViewModel;
    private ActivityRoommatePostBinding binding;
    private String postIdx;

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
                binding.address.setText(roommatePost.getAddress());
                binding.timePeriod.setText(roommatePost.getTime());
                binding.rent.setText(Integer.toString(roommatePost.getRent()));
                binding.bedroomNum.setText(Integer.toString(roommatePost.getBedroomNum()));
                binding.bathroomNum.setText(Integer.toString(roommatePost.getBathroomNum()));
                binding.furnished.setText(roommatePost.isFurnished()?"✓":"✗");
                binding.pet.setText(roommatePost.getPet()?"✓":"✗");
                binding.gender.setText(roommatePost.getGender());
                binding.contact.setText(roommatePost.getContact());
                if (roommatePost.getOther() == null || roommatePost.getOther().length() == 0) {
                    binding.llOther.setVisibility(View.INVISIBLE);
                } else {
                    binding.other.setText(roommatePost.getOther());
                }
                postIdx = roommatePost.getIndex();
            }
        });

        roommatePostViewModel.setPost(getIntent().getStringExtra("idx"));

        binding.editBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editBtn:
                startActivity(EditRoommatePostActivity.newIntent(this, postIdx));
                break;

            default:
                break;
        }
    }
}