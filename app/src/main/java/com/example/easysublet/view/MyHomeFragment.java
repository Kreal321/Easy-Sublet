package com.example.easysublet.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.easysublet.R;
import com.example.easysublet.databinding.FragmentMyHomeBinding;
import com.example.easysublet.model.HomePost;
import com.example.easysublet.viewmodel.MyHomeViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyHomeFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "FindHomeFragment";

    private MyHomeViewModel myHomeViewModel;
    private FragmentMyHomeBinding binding;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private SharedPreferences currentUser;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView() is called");

        myHomeViewModel = new ViewModelProvider(this).get(MyHomeViewModel.class);

        binding = FragmentMyHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        currentUser = getActivity().getSharedPreferences("user" ,Context.MODE_PRIVATE);

        binding.addPostBtn.setOnClickListener(this);


        myHomeViewModel.startFecthList("");
        myHomeViewModel.getPostList().observe(getViewLifecycleOwner(), new Observer<List<HomePost>>() {
            @Override
            public void onChanged(List<HomePost> homePosts) {
                recyclerView = binding.recyclerView;
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                postAdapter = new MyHomeFragment.PostAdapter(root.getContext(), homePosts);
                recyclerView.setAdapter(postAdapter);
            }
        });

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

    private class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder>{
        Context mContext;
        List<HomePost> mData;

        public PostAdapter(Context mContext, List<HomePost> mData) {
            this.mContext = mContext;
            this.mData = mData;
        }

        @NonNull
        @Override
        public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.post_list,parent,false);
            return new PostViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
            // bind image here
            HomePost p = mData.get(position);

            //holder.img.setImageResource(p.getImage());
            //TODO: post photo with picasso
            ImageView img = holder.img;
            Picasso.with(getContext()).load(p.getImage()).into(img);
//            Toast.makeText(getContext(), "get image succeed!!", Toast.LENGTH_SHORT).show();

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       mContext.startActivity(HomePostActivity.newIntent(mContext, p.getIndex()));
//                       Toast.makeText(mContext, "click on post " + p.getIndex(), Toast.LENGTH_SHORT).show();
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