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
import com.example.easysublet.databinding.ActivityRoommatePostBinding;
import com.example.easysublet.model.RoommatePost;
import com.example.easysublet.model.User;
import com.example.easysublet.viewmodel.RoommatePostViewModel;
import com.squareup.picasso.Picasso;

import java.util.Locale;

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
        setLanguage(RoommatePostActivity.this);

        Log.d(TAG, "onCreate() is called");

        roommatePostViewModel = new ViewModelProvider(this).get(RoommatePostViewModel.class);
        binding = ActivityRoommatePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        roommatePostViewModel.getPost().observe(this, new Observer<RoommatePost>() {
            @Override
            public void onChanged(RoommatePost roommatePost) {
                binding.title.setText(roommatePost.getTitle());
//                binding.image.setImageResource(roommatePost.getImage());

                //TODO: post photo with picasso
                ImageView img = binding.image;
                Picasso.with(getApplication()).load(roommatePost.getImage()).into(img);
//                Toast.makeText(getApplication(), "get image succeed!!", Toast.LENGTH_SHORT).show();

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

                roommatePostViewModel.fectchUser();
                roommatePostViewModel.getUserMutableLiveData().observe(RoommatePostActivity.this, new Observer<User>() {
                    @Override
                    public void onChanged(User User) {
                        if(!User.getUid().equals(roommatePost.getUsername())) {
                            binding.editBtn.setVisibility(View.INVISIBLE);
                            Log.d("TEST Home PostActivity",User.getUid()+"| |"+roommatePost.getUsername());
                        }else{
                            binding.editBtn.setOnClickListener(RoommatePostActivity.this);
                        }
                    }
                });

            }
        });

        roommatePostViewModel.setPost(getIntent().getStringExtra("idx"));

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