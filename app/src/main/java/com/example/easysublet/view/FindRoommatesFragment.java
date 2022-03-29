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
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.easysublet.Post;
import com.example.easysublet.PostAdapter;
import com.example.easysublet.R;
import com.example.easysublet.databinding.FragmentFindRoommatesBinding;
import com.example.easysublet.databinding.FragmentFindRoommatesBinding;
import com.example.easysublet.viewmodel.FindRoommatesViewModel;

import java.util.ArrayList;
import java.util.List;

public class FindRoommatesFragment extends Fragment {

    private static final String TAG = "FindRoommatesFragment";
    private FragmentFindRoommatesBinding binding;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView() is called");

        FindRoommatesViewModel findRoommatesViewModel =
                new ViewModelProvider(this).get(FindRoommatesViewModel.class);

        binding = FragmentFindRoommatesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        findRoommatesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        List<Post> list = new ArrayList<>();
        list.add(new Post(R.drawable.apart1));
        list.add(new Post(R.drawable.apart2));
        list.add(new Post(R.drawable.house1));
        list.add(new Post(R.drawable.house2));
        list.add(new Post(R.drawable.house3));
        postAdapter = new PostAdapter(root.getContext(),list);
        recyclerView.setAdapter(postAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}