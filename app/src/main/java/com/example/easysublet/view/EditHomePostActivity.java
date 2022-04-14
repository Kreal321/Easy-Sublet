package com.example.easysublet.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.easysublet.R;
import com.example.easysublet.databinding.ActivityEditHomePostBinding;
import com.example.easysublet.model.HomePost;
import com.example.easysublet.viewmodel.EditHomePostViewModel;
import com.example.easysublet.viewmodel.HomePostViewModel;
import com.squareup.picasso.Picasso;

public class EditHomePostActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "HomePostActivity";

    private HomePostViewModel homePostViewModel;
    private EditHomePostViewModel editHomePostViewModel;
    private ActivityEditHomePostBinding binding;
    private MutableLiveData<HomePost> thisPost;
    private String imagePath;
    private Uri imageUri;

    public static Intent newIntent(Context packageContext, String idx){
        Intent intent = new Intent(packageContext, EditHomePostActivity.class);
        intent.putExtra("idx", idx);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisPost= new MutableLiveData<HomePost>();
        Log.d(TAG, "onCreate() is called");

        homePostViewModel = new ViewModelProvider(this).get(HomePostViewModel.class);
        binding = ActivityEditHomePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        editHomePostViewModel = new EditHomePostViewModel(getApplication());

        homePostViewModel.getPost().observe(this, new Observer<HomePost>() {
            @Override
            public void onChanged(HomePost homePost) {
                thisPost.postValue(homePost);

                binding.titleEntry.setText(homePost.getTitle());

                //binding.postPhoto.setImageResource(homePost.getImage());
                //TODO: post photo with picasso
                ImageView img = binding.postPhoto;
                Picasso.with(getApplication()).load(homePost.getImage()).into(img);
                Toast.makeText(getApplication(), "get image succeed!!", Toast.LENGTH_SHORT).show();

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
        binding.postPhoto.setOnClickListener(this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null) {
            binding.postPhoto.setImageURI(data.getData());
            imagePath = data.getData().getPath();
            Log.d(TAG, "onActivityResult: " + imagePath);
            imageUri = data.getData();

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updateBtn:
                thisPost.observe(this, new Observer<HomePost>() {
                    @Override
                    public void onChanged(HomePost homePost) {
                        if(homePost != null){
                            Log.d(TAG, "Entered Here 1:");
                            editHomePostViewModel.updatePost(homePost.getIndex(),homePost.getUsername(), binding.titleEntry.getText().toString(), homePost.isActive(),binding.addressEntry.getText().toString(), binding.timeEntry.getText().toString(), Integer.parseInt(binding.rentEntry.getText().toString()), binding.contactEntry.getText().toString(), Integer.parseInt(binding.bathroomEntry.getText().toString()), Integer.parseInt(binding.bedroomEntry.getText().toString()), binding.genderEntry.getText().toString(), binding.cbPet.isChecked(), binding.cbFurnished.isChecked(), binding.infoEntry.getText().toString(), homePost.getImage());
                            finish();
                        }

                    }
                });
                //TODO: need inputs checking

                break;

            case R.id.postPhoto:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
                break;

            default:
                break;
        }
    }
}