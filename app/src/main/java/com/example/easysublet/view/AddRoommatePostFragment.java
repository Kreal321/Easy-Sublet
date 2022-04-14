package com.example.easysublet.view;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.easysublet.R;
import com.example.easysublet.databinding.FragmentAddRoommatePostBinding;
import com.example.easysublet.model.User;
import com.example.easysublet.viewmodel.AddRoommatePostViewModel;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.TimeZone;

public class AddRoommatePostFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "FindRoommatesFragment";
    private FragmentAddRoommatePostBinding binding;
    private AddRoommatePostViewModel addRoommatePostViewModel;
    private SharedPreferences currentUser;
    private String imagePath;
    private Uri imageUri;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView() is called");

        addRoommatePostViewModel = new ViewModelProvider(requireActivity()).get(AddRoommatePostViewModel.class);

        currentUser = getActivity().getSharedPreferences("user" , Context.MODE_PRIVATE);

        binding = FragmentAddRoommatePostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.createBtn.setOnClickListener(this);
        binding.postPhoto.setOnClickListener(this);

        handleDatePicker();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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
                materialDatePicker.show(getParentFragmentManager(),"DATE_PICKER");
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
        }

    }

    public Boolean postInputCheck() {
        if(binding.titleEntry.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Title cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(binding.addressEntry.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Address cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(binding.timeEntry.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Time cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(binding.rentEntry.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Rent cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(binding.contactEntry.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Contact cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(binding.bathroomEntry.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Bathroom cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(binding.bedroomEntry.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Bedroom cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(binding.genderEntry.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Gender cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createBtn:
                addRoommatePostViewModel.fetchUser();
                //TODO: need inputs checking
                addRoommatePostViewModel.getUserMutableLiveData().observe(getActivity(), new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        if(!postInputCheck()) return;

                        if(user != null){
                            Log.d(TAG, "Entered Here 1:");
                            if(imageUri!= null){
                                Log.d(TAG, "Entered Here 2:");
                                addRoommatePostViewModel.uploadImage(imageUri);
                                addRoommatePostViewModel.getUploadedUri().observe(getViewLifecycleOwner(), new Observer<Uri>() {
                                    @Override
                                    public void onChanged(Uri uri) {
                                        Log.d(TAG, "Entered Here 3:");
                                        addRoommatePostViewModel.createPost(user.getUid(), binding.titleEntry.getText().toString(), binding.addressEntry.getText().toString(), binding.timeEntry.getText().toString(), Integer.parseInt(binding.rentEntry.getText().toString()), binding.contactEntry.getText().toString(), Integer.parseInt(binding.bathroomEntry.getText().toString()), Integer.parseInt(binding.bedroomEntry.getText().toString()), binding.genderEntry.getText().toString(), binding.cbPet.isChecked(), binding.cbFurnished.isChecked(), binding.infoEntry.getText().toString(), uri.toString());
                                        getActivity().finish();
                                    }
                                });
                            }
                        }
                    }
                });
                //addRoommatePostViewModel.createPost(currentUser.getString("username", null), binding.titleEntry.getText().toString(), binding.addressEntry.getText().toString(), binding.timeEntry.getText().toString(), Integer.parseInt(binding.rentEntry.getText().toString()), binding.contactEntry.getText().toString(), Integer.parseInt(binding.bathroomEntry.getText().toString()), Integer.parseInt(binding.bedroomEntry.getText().toString()), binding.genderEntry.getText().toString(), binding.cbPet.isChecked(), binding.cbFurnished.isChecked(), binding.infoEntry.getText().toString(), R.drawable.apart1);
                //getActivity().finish();
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
