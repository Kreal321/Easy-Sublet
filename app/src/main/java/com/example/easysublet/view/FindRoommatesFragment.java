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

import com.example.easysublet.databinding.FragmentFindRoommatesBinding;
import com.example.easysublet.databinding.FragmentFindRoommatesBinding;
import com.example.easysublet.viewmodel.FindRoommatesViewModel;

public class FindRoommatesFragment extends Fragment {

    private static final String TAG = "FindRoommatesFragment";
    private FragmentFindRoommatesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView() is called");

        FindRoommatesViewModel findRoommatesViewModel =
                new ViewModelProvider(this).get(FindRoommatesViewModel.class);

        binding = FragmentFindRoommatesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        findRoommatesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}