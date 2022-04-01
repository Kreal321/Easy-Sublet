package com.example.easysublet.view;

import android.content.Intent;
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
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.example.easysublet.model.Post;
import com.example.easysublet.PostAdapter;

import com.example.easysublet.R;
import com.example.easysublet.databinding.FragmentFindHomeBinding;
import com.example.easysublet.model.User;
import com.example.easysublet.repository.mainRepo;
import com.example.easysublet.viewmodel.FindHomeViewModel;
import com.example.easysublet.viewmodel.ProfileViewModel;

import java.util.ArrayList;
import java.util.List;

public class FindHomeFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "FindHomeFragment";

    private FindHomeViewModel findHomeViewModel;
    private FragmentFindHomeBinding binding;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView() is called");

        findHomeViewModel = new ViewModelProvider(this).get(FindHomeViewModel.class);

        binding = FragmentFindHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        findHomeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        binding.addPostBtn.setOnClickListener(this);

        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        List<Post> list = mainRepo.getList();
        postAdapter = new PostAdapter(getActivity(), list);
        recyclerView.setAdapter(postAdapter);

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
                startActivity(AddPostActivity.newIntent(getActivity(), 0));
                Toast.makeText(getActivity().getApplicationContext(), "Add post", Toast.LENGTH_LONG).show();
                break;


            default:
                break;
        }
    }


}