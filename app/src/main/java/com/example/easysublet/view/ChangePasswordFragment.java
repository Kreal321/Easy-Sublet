package com.example.easysublet.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.easysublet.R;
import com.example.easysublet.databinding.FragmentChangePasswordBinding;
import com.example.easysublet.model.User;
import com.example.easysublet.viewmodel.SettingViewModel;

public class ChangePasswordFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "PostFragment";
    private static final int REQUEST_CODE_SETTING = 0;

    private SettingViewModel settingViewModel;
    private FragmentChangePasswordBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() is called");

        settingViewModel = new ViewModelProvider(requireActivity()).get(SettingViewModel.class);

        binding = FragmentChangePasswordBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.confirmBtn.setOnClickListener(this);
        view.bringToFront();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() is called");


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirmBtn:
                Log.d("onclick test", "onclick() is called");
                settingViewModel.getUser().observe(this, new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        if (!binding.newPasswordEntry.getText().toString().equals(binding.confirmPasswordEntry.getText().toString())) {
                            Toast.makeText(getActivity(), "Two passwords are different", Toast.LENGTH_SHORT).show();
                            binding.oldPasswordEntry.setText(null);
                            binding.newPasswordEntry.setText(null);
                            binding.confirmPasswordEntry.setText(null);
                        }
                        else if (!settingViewModel.getUser().getValue().passwordIsCorrect(binding.oldPasswordEntry.getText().toString())){
                            Toast.makeText(getActivity(), "Old password is not correct", Toast.LENGTH_SHORT).show();
                            binding.oldPasswordEntry.setText(null);
                            binding.newPasswordEntry.setText(null);
                            binding.confirmPasswordEntry.setText(null);
                        } else {
                            Log.d("onclick", "else is entered");
                            settingViewModel.changePassword(binding.newPasswordEntry.getText().toString());
                        }
                    }
                });
                break;

            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}