package com.example.easysublet.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.easysublet.databinding.ActivityEditRoommatePostBinding;
import com.example.easysublet.model.RoommatePost;
import com.example.easysublet.viewmodel.EditRoommatePostViewModel;
import com.example.easysublet.viewmodel.RoommatePostViewModel;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.TimeZone;

public class EditRoommatePostActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "RoommatePostActivity";

    private RoommatePostViewModel roommatePostViewModel;
    private ActivityEditRoommatePostBinding binding;
    private MutableLiveData<RoommatePost> thisPost;
    private EditRoommatePostViewModel editRoommatePostViewModel;
    private String imagePath;
    private Uri imageUri;
    private boolean changeImage;

    public static Intent newIntent(Context packageContext, String idx){
        Intent intent = new Intent(packageContext, EditRoommatePostActivity.class);
        intent.putExtra("idx", idx);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeImage = false;
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
//                Toast.makeText(getApplication(), "get image succeed!!", Toast.LENGTH_SHORT).show();

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
        binding.postPhoto.setOnClickListener(this);

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
                editRoommatePostViewModel.uploadImage(imageUri);
                editRoommatePostViewModel.getUriMutableLiveData().observe(this, new Observer<Uri>() {
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
                            SharedPreferences uriStored = getSharedPreferences("uri",Context.MODE_PRIVATE);
                            String uri = uriStored.getString("uri",null);
                            if(uri!= null && changeImage == true){
                                Log.d(TAG, "Image changed");
                                editRoommatePostViewModel.updatePost(roommatePost.getIndex(),roommatePost.getUsername(), binding.titleEntry.getText().toString(), roommatePost.isActive(),binding.addressEntry.getText().toString(), binding.timeEntry.getText().toString(), Integer.parseInt(binding.rentEntry.getText().toString()), binding.contactEntry.getText().toString(), Integer.parseInt(binding.bathroomEntry.getText().toString()), Integer.parseInt(binding.bedroomEntry.getText().toString()), binding.genderEntry.getText().toString(), binding.cbPet.isChecked(), binding.cbFurnished.isChecked(), binding.infoEntry.getText().toString(), uri);
                            }else {
                                Log.d(TAG, "Image no changed "+changeImage);
                                editRoommatePostViewModel.updatePost(roommatePost.getIndex(),roommatePost.getUsername(), binding.titleEntry.getText().toString(), roommatePost.isActive(),binding.addressEntry.getText().toString(), binding.timeEntry.getText().toString(), Integer.parseInt(binding.rentEntry.getText().toString()), binding.contactEntry.getText().toString(), Integer.parseInt(binding.bathroomEntry.getText().toString()), Integer.parseInt(binding.bedroomEntry.getText().toString()), binding.genderEntry.getText().toString(), binding.cbPet.isChecked(), binding.cbFurnished.isChecked(), binding.infoEntry.getText().toString(), roommatePost.getImage());
                            }
                            finish();
                        }
                    }
                });
                break;

            case R.id.postPhoto:
                changeImage = true;
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
                break;

            default:
                break;
        }
    }
}