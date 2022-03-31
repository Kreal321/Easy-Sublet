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
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.example.easysublet.model.Post;
import com.example.easysublet.PostAdapter;

import com.example.easysublet.R;
import com.example.easysublet.databinding.FragmentFindRoommatesBinding;
import com.example.easysublet.databinding.FragmentFindRoommatesBinding;
import com.example.easysublet.viewmodel.FindRoommatesViewModel;


import java.util.ArrayList;
import java.util.List;


public class FindRoommatesFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "FindRoommatesFragment";

    private FindRoommatesViewModel findRoommatesViewModel;
    private FragmentFindRoommatesBinding binding;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView() is called");

        findRoommatesViewModel = new ViewModelProvider(this).get(FindRoommatesViewModel.class);

        binding = FragmentFindRoommatesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        findRoommatesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        List<Post> list = new ArrayList<>();
        list.add(new Post("This is post 1", R.drawable.apart1, 1));
        list.add(new Post("This is post 2",R.drawable.apart2, 2));
        list.add(new Post("This is post 3",R.drawable.house1, 3));
        list.add(new Post("This is post 4",R.drawable.house2, 4));
        list.add(new Post("This is post 5",R.drawable.house3, 5));
        postAdapter = new PostAdapter(root.getContext(), list);
        recyclerView.setAdapter(postAdapter);

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