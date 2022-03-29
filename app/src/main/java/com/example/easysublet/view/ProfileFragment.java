package com.example.easysublet.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.easysublet.R;
import com.example.easysublet.databinding.FragmentProfileBinding;
import com.example.easysublet.viewmodel.ProfileViewModel;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


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
            case R.id.changePwBtn:
                Toast.makeText(getActivity().getApplicationContext(), "Change password", Toast.LENGTH_LONG).show();
                break;

            case R.id.deleteAccountBtn:
                Toast.makeText(getActivity().getApplicationContext(), "Delete account", Toast.LENGTH_LONG).show();
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