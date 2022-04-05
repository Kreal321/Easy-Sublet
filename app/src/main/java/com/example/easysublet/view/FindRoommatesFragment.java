package com.example.easysublet.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.example.easysublet.model.HomePost;
import com.example.easysublet.model.RoommatePost;

import com.example.easysublet.R;
import com.example.easysublet.databinding.FragmentFindRoommatesBinding;
import com.example.easysublet.repository.mainRepo;
import com.example.easysublet.viewmodel.FindRoommatesViewModel;


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


        binding.addPostBtn.setOnClickListener(this);
//        binding.mapButton.setOnClickListener(this);


        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        List<RoommatePost> list = mainRepo.getRoommatePostList();
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
                startActivity(AddPostActivity.newIntent(getActivity(), 1));
                Toast.makeText(getActivity().getApplicationContext(), "Add post", Toast.LENGTH_LONG).show();
                break;

//            case R.id.map_button:
//                startActivity(new Intent(getActivity(), MapActivity.class));
//                break;

            default:
                break;
        }
    }


    private class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder>{
        Context mContext;
        List<RoommatePost> mData;

        public PostAdapter(Context mContext, List<RoommatePost> mData) {
            this.mContext = mContext;
            this.mData = mData;
        }

        @NonNull
        @Override
        public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.post_list,parent,false);
            return new PostViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
            // bind image here
            RoommatePost p = mData.get(position);
            holder.img.setImageResource(p.getImage());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       mContext.startActivity(RoommatePostActivity.newIntent(mContext, p.getIndex()));
                       Toast.makeText(mContext, "click on post " + p.getIndex(), Toast.LENGTH_SHORT).show();
                   }
               }
            );
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class PostViewHolder extends RecyclerView.ViewHolder{
            ImageView img;
            public PostViewHolder(@NonNull View itemView){
                super(itemView);
                img = itemView.findViewById(R.id.post_image);
            }
        }
    }

}