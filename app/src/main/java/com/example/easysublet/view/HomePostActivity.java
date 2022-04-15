package com.example.easysublet.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.easysublet.R;
import com.example.easysublet.databinding.ActivityHomePostBinding;
import com.example.easysublet.model.HomePost;
import com.example.easysublet.model.User;
import com.example.easysublet.viewmodel.HomePostViewModel;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class HomePostActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "HomePostActivity";

    private HomePostViewModel homePostViewModel;
    private ActivityHomePostBinding binding;
    private String postIdx;

    public static Intent newIntent(Context packageContext, String idx){
        Intent intent = new Intent(packageContext, HomePostActivity.class);
        intent.putExtra("idx", idx);
        return intent;
    }

    public static void setLanguage(Activity activity) {
        SharedPreferences user = activity.getSharedPreferences("user" , Context.MODE_PRIVATE);
        String language = user.getString("language", "en");

        if (language.equals("zh")) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            Resources resources = activity.getResources();
            Configuration config = resources.getConfiguration();
            config.setLocale(locale);
            resources.updateConfiguration(config, resources.getDisplayMetrics());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() is called");
        setLanguage(HomePostActivity.this);
        homePostViewModel = new ViewModelProvider(this).get(HomePostViewModel.class);
        binding = ActivityHomePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        homePostViewModel.getPost().observe(this, new Observer<HomePost>() {
            @Override
            public void onChanged(HomePost homePost) {
                binding.title.setText(homePost.getTitle());

                //binding.image.setImageResource(homePost.getImage());
                //TODO: post photo with picasso
                ImageView img = binding.image;
                Picasso.with(getApplication()).load(homePost.getImage()).into(img);
//                Toast.makeText(getApplication(), "get image succeed!!", Toast.LENGTH_SHORT).show();

                binding.address.setText(homePost.getAddress());
                binding.timePeriod.setText(homePost.getTime());
                binding.rent.setText(Integer.toString(homePost.getRent()));
                binding.bedroomNum.setText(Integer.toString(homePost.getBedroomNum()));
                binding.bathroomNum.setText(Integer.toString(homePost.getBathroomNum()));
                binding.furnished.setText(homePost.isFurnished()?"✓":"✗");
                binding.pet.setText(homePost.getPet()?"✓":"✗");
                binding.gender.setText(homePost.getGender());
                binding.contact.setText(homePost.getContact());
                if (homePost.getOther() == null || homePost.getOther().length() == 0) {
                    binding.llOther.setVisibility(View.INVISIBLE);
                } else {
                    binding.other.setText(homePost.getOther());
                }
                postIdx = homePost.getIndex();
                homePostViewModel.fectchUser();
                homePostViewModel.getUserMutableLiveData().observe(HomePostActivity.this, new Observer<User>() {
                    @Override
                    public void onChanged(User User) {
                        if(!User.getUid().equals(homePost.getUsername())) {
                            binding.editBtn.setVisibility(View.INVISIBLE);
                            Log.d("TEST Home PostActivity",User.getUid()+"| |"+homePost.getUsername());
                        }else{
                            binding.editBtn.setOnClickListener(HomePostActivity.this);
                        }
                    }
                });

            }
        });


        homePostViewModel.setPost(getIntent().getStringExtra("idx"));
        

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editBtn:
                startActivity(EditHomePostActivity.newIntent(this, postIdx));
                break;

            default:
                break;
        }
    }
}