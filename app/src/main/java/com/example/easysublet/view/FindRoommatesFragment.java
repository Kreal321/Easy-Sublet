package com.example.easysublet.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.easysublet.R;
import com.example.easysublet.databinding.FragmentFindRoommatesBinding;
import com.example.easysublet.databinding.FragmentFindRoommatesBinding;
import com.example.easysublet.viewmodel.FindRoommatesViewModel;

public class FindRoommatesFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "FindRoommatesFragment";

    private FindRoommatesViewModel findRoommatesViewModel;
    private FragmentFindRoommatesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView() is called");

        findRoommatesViewModel = new ViewModelProvider(this).get(FindRoommatesViewModel.class);

        binding = FragmentFindRoommatesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        findRoommatesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        binding.addPostBtn.setOnClickListener(this);

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
            case R.id.addPostBtn:
                startActivity(AddPostActivity.newIntent(getActivity(), 1));
                Toast.makeText(getActivity().getApplicationContext(), "Add post", Toast.LENGTH_LONG).show();
                break;


            default:
                break;
        }
    }

}