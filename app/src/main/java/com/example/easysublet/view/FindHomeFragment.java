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
        List<Post> list = new ArrayList<>();
        list.add(new Post("This is post 1", R.drawable.apart1, 1));
        list.add(new Post("This is post 2",R.drawable.apart2, 2));
        list.add(new Post("This is post 3",R.drawable.house1, 3));
        list.add(new Post("This is post 4",R.drawable.house2, 4));
        list.add(new Post("This is post 5",R.drawable.house3, 5));
        list.add(new Post("This is post 5",R.drawable.house3, 5));
        list.add(new Post("This is post 6", R.drawable.apart1, 6));
        list.add(new Post("This is post 7",R.drawable.apart2, 7));
        list.add(new Post("This is post 8",R.drawable.house1, 8));
        list.add(new Post("This is post 9",R.drawable.house2, 9));
        list.add(new Post("This is post 10",R.drawable.house3, 10));
        list.add(new Post("This is post 11", R.drawable.apart1, 11));
        list.add(new Post("This is post 12",R.drawable.apart2, 12));
        list.add(new Post("This is post 13",R.drawable.house1, 13));
        list.add(new Post("This is post 14",R.drawable.house2, 14));
        list.add(new Post("This is post 15",R.drawable.house3, 15));
        postAdapter = new PostAdapter(root.getContext(), list);
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