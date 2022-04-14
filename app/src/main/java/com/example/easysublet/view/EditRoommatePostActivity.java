package com.example.easysublet.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.easysublet.R;
import com.example.easysublet.databinding.ActivityEditRoommatePostBinding;
import com.example.easysublet.model.RoommatePost;
import com.example.easysublet.viewmodel.EditRoommatePostViewModel;
import com.example.easysublet.viewmodel.RoommatePostViewModel;
import com.squareup.picasso.Picasso;

public class EditRoommatePostActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "RoommatePostActivity";

    private RoommatePostViewModel roommatePostViewModel;
    private ActivityEditRoommatePostBinding binding;
    private MutableLiveData<RoommatePost> thisPost;
    private EditRoommatePostViewModel editRoommatePostViewModel;

    public static Intent newIntent(Context packageContext, String idx){
        Intent intent = new Intent(packageContext, EditRoommatePostActivity.class);
        intent.putExtra("idx", idx);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        thisPost = new MutableLiveData<RoommatePost>();

        Log.d(TAG, "onCreate() is called");

        roommatePostViewModel = new ViewModelProvider(this).get(RoommatePostViewModel.class);
        binding = ActivityEditRoommatePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        editRoommatePostViewModel = new EditRoommatePostViewModel(getApplication());

        roommatePostViewModel.getPost().observe(this, new Observer<RoommatePost>() {
            @Override
            public void onChanged(RoommatePost roommatePost) {
                binding.titleEntry.setText(roommatePost.getTitle());
//              binding.postPhoto.setImageResource(roommatePost.getImage());

                thisPost.postValue(roommatePost);

                //TODO: post photo with picasso
                ImageView img = binding.postPhoto;
                Picasso.with(getApplication()).load(roommatePost.getImage()).into(img);
                Toast.makeText(getApplication(), "get image succeed!!", Toast.LENGTH_SHORT).show();

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
                thisPost.observe(this, new Observer<RoommatePost>() {
                    @Override
                    public void onChanged(RoommatePost roommatePost) {
                        if(roommatePost != null){
                            Log.d(TAG, "Entered Here 1:");
                            editRoommatePostViewModel.updatePost(roommatePost.getIndex(),roommatePost.getUsername(), binding.titleEntry.getText().toString(), roommatePost.isActive(),binding.addressEntry.getText().toString(), binding.timeEntry.getText().toString(), Integer.parseInt(binding.rentEntry.getText().toString()), binding.contactEntry.getText().toString(), Integer.parseInt(binding.bathroomEntry.getText().toString()), Integer.parseInt(binding.bedroomEntry.getText().toString()), binding.genderEntry.getText().toString(), binding.cbPet.isChecked(), binding.cbFurnished.isChecked(), binding.infoEntry.getText().toString(), roommatePost.getImage());
                            finish();
                        }

                    }
                });
                break;

            default:
                break;
        }
    }
}