package com.example.easysublet.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.easysublet.R;
import com.example.easysublet.databinding.ActivityEditHomePostBinding;
import com.example.easysublet.model.HomePost;
import com.example.easysublet.repository.helperRepo;
import com.example.easysublet.viewmodel.EditHomePostViewModel;
import com.example.easysublet.viewmodel.HomePostViewModel;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class EditHomePostActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "HomePostActivity";

    private HomePostViewModel homePostViewModel;
    private EditHomePostViewModel editHomePostViewModel;
    private ActivityEditHomePostBinding binding;
    private MutableLiveData<HomePost> thisPost;
    private String imagePath;
    private Uri imageUri;
    private boolean changeImage;

    public static Intent newIntent(Context packageContext, String idx){
        Intent intent = new Intent(packageContext, EditHomePostActivity.class);
        intent.putExtra("idx", idx);
        return intent;
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
        thisPost= new MutableLiveData<HomePost>();
        changeImage = false;

        Log.d(TAG, "onCreate() is called");
        setLanguage(EditHomePostActivity.this);

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
//                Toast.makeText(getApplication(), "get image succeed!!", Toast.LENGTH_SHORT).show();

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
        ((TextInputLayout) binding.tilAddressEntry).setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MapActivity.newIntent(EditHomePostActivity.this));
//                binding.addressEntry.setText("Auto set Address");
            }
        });

        handleDatePicker();

    }

    private void handleDatePicker() {

        TextInputLayout dateTextInput = binding.tilTimePicker;
        EditText dateEditText = dateTextInput.getEditText();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();
        long today = MaterialDatePicker.todayInUtcMilliseconds();

        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setStart(today);
        constraintsBuilder.setValidator(DateValidatorPointForward.now());

        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("SELECT DATE");
        builder.setCalendarConstraints(constraintsBuilder.build());

        final MaterialDatePicker<Pair<Long, Long>> materialDatePicker = builder.build();
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKER");
            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                dateEditText.setText(materialDatePicker.getHeaderText());
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null) {
            binding.postPhoto.setImageURI(data.getData());
            imagePath = data.getData().getPath();
            Log.d(TAG, "onActivityResult: " + imagePath);
            imageUri = data.getData();
            if(imageUri!= null && changeImage){
                Log.d(TAG, "Entered Here 2:");
                editHomePostViewModel.uploadImage(imageUri);
                editHomePostViewModel.getUriMutableLiveData().observe(this, new Observer<Uri>() {
                    @Override
                    public void onChanged(Uri uri) {
                        Log.d(TAG, "Entered Here 3:");
                        SharedPreferences sharedPref = getApplication().getSharedPreferences("uri",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("uri",uri.toString());
                        editor.apply();
                        editor.commit();
                    }
                });
            }
        }

    }

    public Boolean postInputCheck() {
        if(binding.titleEntry.getText().toString().length() == 0) {
            Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(binding.addressEntry.getText().toString().length() == 0) {
            Toast.makeText(this, "Address cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(binding.timeEntry.getText().toString().length() == 0) {
            Toast.makeText(this, "Time cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(binding.rentEntry.getText().toString().length() == 0) {
            Toast.makeText(this, "Rent cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(binding.contactEntry.getText().toString().length() == 0) {
            Toast.makeText(this, "Contact cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(binding.bathroomEntry.getText().toString().length() == 0) {
            Toast.makeText(this, "Bathroom cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(binding.bedroomEntry.getText().toString().length() == 0) {
            Toast.makeText(this, "Bedroom cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(binding.genderEntry.getText().toString().length() == 0) {
            Toast.makeText(this, "Gender cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updateBtn:
                if(helperRepo.isConnected(getApplication())){
                    thisPost.observe(this, new Observer<HomePost>() {
                        @Override
                        public void onChanged(HomePost homePost) {
                            if (!postInputCheck()) return;
                            if (homePost != null) {

                                SharedPreferences uriStored = getSharedPreferences("uri", Context.MODE_PRIVATE);
                                String uri = uriStored.getString("uri", null);
                                if (uri != null && changeImage == true) {
                                    Log.d(TAG, "Image changed");
                                    editHomePostViewModel.updatePost(homePost.getIndex(), homePost.getUsername(), binding.titleEntry.getText().toString(), homePost.isActive(), binding.addressEntry.getText().toString(), binding.timeEntry.getText().toString(), Integer.parseInt(binding.rentEntry.getText().toString()), binding.contactEntry.getText().toString(), Integer.parseInt(binding.bathroomEntry.getText().toString()), Integer.parseInt(binding.bedroomEntry.getText().toString()), binding.genderEntry.getText().toString(), binding.cbPet.isChecked(), binding.cbFurnished.isChecked(), binding.infoEntry.getText().toString(), uri);
                                } else {
                                    Log.d(TAG, "Image no changed " + changeImage);
                                    editHomePostViewModel.updatePost(homePost.getIndex(), homePost.getUsername(), binding.titleEntry.getText().toString(), homePost.isActive(), binding.addressEntry.getText().toString(), binding.timeEntry.getText().toString(), Integer.parseInt(binding.rentEntry.getText().toString()), binding.contactEntry.getText().toString(), Integer.parseInt(binding.bathroomEntry.getText().toString()), Integer.parseInt(binding.bedroomEntry.getText().toString()), binding.genderEntry.getText().toString(), binding.cbPet.isChecked(), binding.cbFurnished.isChecked(), binding.infoEntry.getText().toString(), homePost.getImage());
                                }
                                finish();
                            }

                        }
                    });
                    //TODO: need inputs checking
                }
                break;

            case R.id.postPhoto:
                changeImage= true;
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
                break;

            default:
                break;
        }
    }
}