package com.example.easysublet.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.easysublet.R;
import com.example.easysublet.databinding.FragmentProfileBinding;
import com.example.easysublet.model.User;
import com.example.easysublet.viewmodel.ProfileViewModel;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    private FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;
    private static final String TAG = "ProfileFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        SharedPreferences emailStored = getActivity().getSharedPreferences("email",Context.MODE_PRIVATE);
        String email = emailStored.getString("email",null);
        Log.d(TAG, "onCreateView() is called. User email: " + email);
        profileViewModel.setUser(email);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.updateInfoBtn.setOnClickListener(this);
        binding.changePwBtn.setOnClickListener(this);
        binding.deleteAccountBtn.setOnClickListener(this);
        binding.logoutBtn.setOnClickListener(this);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updateInfoBtn:
                profileViewModel.updateInfo(binding.usernameEntry.getText().toString(), binding.emailEntry.getText().toString());
                break;

            case R.id.changePwBtn:
                profileViewModel.getUser().observe(this, new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        if (!binding.newPasswordEntry.getText().toString().equals(binding.confirmPasswordEntry.getText().toString())) {
                            Toast.makeText(getActivity(), "Two passwords are different", Toast.LENGTH_SHORT).show();
                            binding.oldPasswordEntry.setText(null);
                            binding.newPasswordEntry.setText(null);
                            binding.confirmPasswordEntry.setText(null);
                        }
                        else if (!profileViewModel.getUser().getValue().passwordIsCorrect(binding.oldPasswordEntry.getText().toString())){
                            Toast.makeText(getActivity(), "Old password is not correct", Toast.LENGTH_SHORT).show();
                            binding.oldPasswordEntry.setText(null);
                            binding.newPasswordEntry.setText(null);
                            binding.confirmPasswordEntry.setText(null);
                        } else {
                            Log.d("onclick", "else is entered");
                            profileViewModel.changePassword(binding.newPasswordEntry.getText().toString());
                        }
                    }
                });

                Toast.makeText(getActivity().getApplicationContext(), "Change password", Toast.LENGTH_LONG).show();
                break;

            case R.id.deleteAccountBtn:
                profileViewModel.deleteUser();
                break;

            case R.id.logoutBtn:
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction("ACTION_LOGOUT");
                getActivity().sendBroadcast(broadcastIntent);

                break;

            default:
                break;
        }
    }
}