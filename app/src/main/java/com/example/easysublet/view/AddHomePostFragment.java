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

import com.example.easysublet.databinding.FragmentAddHomePostBinding;
import com.example.easysublet.viewmodel.AddPostViewModel;

public class AddHomePostFragment extends Fragment {

    private static final String TAG = "FindRoommatesFragment";
    private FragmentAddHomePostBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView() is called");

        AddPostViewModel addPostViewModel =
                new ViewModelProvider(requireActivity()).get(AddPostViewModel.class);

        binding = FragmentAddHomePostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.text;
        addPostViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
