package com.example.easysublet.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.easysublet.R;
import com.example.easysublet.databinding.FragmentPostBinding;
import com.example.easysublet.viewmodel.HomeViewModel;

public class PostFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "PostFragment";
    private static final int REQUEST_CODE_SETTING = 0;

    private HomeViewModel homeViewModel;
    private FragmentPostBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() is called");

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        binding = FragmentPostBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.settingBtn.setOnClickListener(this);
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
            case R.id.settingBtn:
                Intent intent = SettingActivity.newIntent(getActivity(), homeViewModel.getUser().getValue().getEmail());
                startActivityForResult(intent, REQUEST_CODE_SETTING);

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
