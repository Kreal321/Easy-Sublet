package com.example.easysublet.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.easysublet.databinding.FragmentFindHomeBinding;
import com.example.easysublet.viewmodel.FindHomeViewModel;

public class FindHomeFragment extends Fragment {

    private static final String TAG = "FindHomeFragment";

    private FragmentFindHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView() is called");

        FindHomeViewModel findHomeViewModel =
                new ViewModelProvider(this).get(FindHomeViewModel.class);

        binding = FragmentFindHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        findHomeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}