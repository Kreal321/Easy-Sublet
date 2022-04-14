package com.example.easysublet.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.easysublet.R;
import com.example.easysublet.databinding.FragmentProfileBinding;
import com.example.easysublet.model.HomePost;
import com.example.easysublet.model.RoommatePost;
import com.example.easysublet.model.User;
import com.example.easysublet.viewmodel.ProfileViewModel;

import java.util.List;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    private FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;
    private static final String TAG = "ProfileFragment";
    private SharedPreferences currentUser;
    private String username;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        currentUser = getActivity().getSharedPreferences("user" ,Context.MODE_PRIVATE);
        String email = currentUser.getString("email",null);
        Log.d(TAG, "onCreateView() is called. User email: " + email);
        profileViewModel.setUser(email);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //TODO: add two binding: .HomePostNum, .RoommatePostNum
        profileViewModel.getMyListCount();
        profileViewModel.getHomePostList().observe(getViewLifecycleOwner(), new Observer<List<HomePost>>() {
            @Override
            public void onChanged(List<HomePost> postList) {
                if(postList!= null){
                    binding.HomePostNum.setText(String.valueOf(postList.size()));
                }
            }
        });

        profileViewModel.getRoommatePostList().observe(getViewLifecycleOwner(), new Observer<List<RoommatePost>>() {
            @Override
            public void onChanged(List<RoommatePost> postList) {
                if(postList!= null){
                    binding.RoommatePostNum.setText(String.valueOf(postList.size()));
                }
            }
        });

        binding.updateInfoBtn.setOnClickListener(this);
        binding.changePwBtn.setOnClickListener(this);
        binding.deleteAccountBtn.setOnClickListener(this);
        binding.logoutBtn.setOnClickListener(this);
        binding.roommatePostCard.setOnClickListener(this);
        binding.homePostCard.setOnClickListener(this);

        profileViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user != null){
                    binding.usernameEntry.setText(username);
                    binding.emailEntry.setText(user.getEmail());
                    binding.topAppBar.setTitle(user.getUsername() + "'s Profile");
                    binding.usernameEntry.setText(user.getUsername());
                }
            }
        });
//TODO: change to fecth name from firebase or sharedPreference

//        username = currentUser.getString("username", null);
//        binding.usernameEntry.setText(username);
//        binding.emailEntry.setText(currentUser.getString("email", null));
//
//        binding.topAppBar.setTitle(username + "'s Profile");

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
            case R.id.homePostCard:
                startActivity(MyPostActivity.newIntent(getActivity(), 0));
                break;

            case R.id.roommatePostCard:
                startActivity(MyPostActivity.newIntent(getActivity(), 1));
                break;

            case R.id.updateInfoBtn:
                profileViewModel.updateInfo(binding.usernameEntry.getText().toString(), binding.emailEntry.getText().toString());
                break;

            case R.id.changePwBtn:
                String oldPassword = binding.oldPasswordEntry.getText().toString();
                String newPassword1 = binding.newPasswordEntry.getText().toString();
                String newPassword2 = binding.confirmPasswordEntry.getText().toString();
                Log.d("onclick test", "onclick() is called");
                if (!newPassword1.equals(newPassword2)) {
                    Toast.makeText(getActivity(), "Two passwords are different", Toast.LENGTH_SHORT).show();

                }else if(newPassword1.length()<6){
                    Toast.makeText(getActivity(), "password length should exceed or equal to 6", Toast.LENGTH_SHORT).show();
                }
                else{
                    profileViewModel.changePassword(oldPassword, newPassword1);
                }
                binding.oldPasswordEntry.setText(null);
                binding.newPasswordEntry.setText(null);
                binding.confirmPasswordEntry.setText(null);
//              Toast.makeText(getActivity().getApplicationContext(), "Change password", Toast.LENGTH_LONG).show();
                break;

            case R.id.deleteAccountBtn:
                profileViewModel.deleteUser();
                getActivity().finish();
                break;

            case R.id.logoutBtn:
                profileViewModel.logout();//NOTE: logout firebase
                getActivity().finish();
                break;

            default:
                break;
        }
    }
}