package com.example.easysublet.view;

import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.easysublet.R;
import com.example.easysublet.databinding.FragmentFindHomeBinding;
import com.example.easysublet.model.HomePost;
import com.example.easysublet.repository.helperRepo;
import com.example.easysublet.viewmodel.FindHomeViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FindHomeFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "FindHomeFragment";

    private FindHomeViewModel findHomeViewModel;
    private FragmentFindHomeBinding binding;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private SwipeRefreshLayout mySwipeRefreshLayout;

    public FindHomeFragment(){}

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView() is called");

        //findHomeViewModel = new ViewModelProvider(this).get(FindHomeViewModel.class);
        findHomeViewModel = new FindHomeViewModel(getActivity().getApplication());
        binding = FragmentFindHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //TODO: test


        binding.addPostBtn.setOnClickListener(this);
        binding.searchBtn.setOnClickListener(this);
        //binding.mapButton.setOnClickListener(this);

        findHomeViewModel.startFecthList();
        findHomeViewModel.getHomePostList().observe(getViewLifecycleOwner(), new Observer<List<HomePost>>() {
            @Override
            public void onChanged(List<HomePost> homePosts) {
                    Log.d(TAG, "getHomePostList() called:DEBUG" + homePosts.size());
                    recyclerView = binding.recyclerView;
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    postAdapter = new FindHomeFragment.PostAdapter(root.getContext(), homePosts);
                    recyclerView.setAdapter(postAdapter);
            }
        });

        refreshScreen();//A placebo refresh Feature

        return root;
    }

    private void refreshScreen(){
        /*
         * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
         * performs a swipe-to-refresh gesture.
         */
        mySwipeRefreshLayout = (SwipeRefreshLayout) binding.swiperefresh;
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        if(helperRepo.isConnected(getActivity().getApplication())) {
                            Log.i(TAG, "onRefresh called from SwipeRefreshLayout");

                            // This method performs the actual data-refresh operation.
                            // The method calls setRefreshing(false) when it's finished.
                            //myUpdateOperation();
                            Toast.makeText(getActivity(), "Refreshed", Toast.LENGTH_SHORT).show();
                            findHomeViewModel.startFecthList();
                            findHomeViewModel.getHomePostList().observe(getViewLifecycleOwner(), new Observer<List<HomePost>>() {
                                @Override
                                public void onChanged(List<HomePost> homePosts) {
                                    if (!homePosts.equals(postAdapter.mData)) {
                                        Log.d(TAG, "getHomePostList() called:DEBUG" + homePosts.size());
                                        postAdapter.clear();
                                        postAdapter.addAll(homePosts);
                                    }
                                }
                            });
                        }
                        binding.swiperefresh.setRefreshing(false);

                    }
                }
        );
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
//                Toast.makeText(getActivity().getApplicationContext(), "Add post", Toast.LENGTH_LONG).show();
                break;

            case R.id.searchBtn:
                if(helperRepo.isConnected(getActivity().getApplication())){
                    findHomeViewModel.getFilteredPostList(binding.searchEntry.getText().toString());
                }
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
        List<HomePost> mData;

        public PostAdapter(Context mContext, List<HomePost> mData) {
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
            HomePost p = mData.get(position);
            //holder.img.setImageResource(p.getImage());
            ImageView img = holder.img;
            Picasso.with(getActivity()).load(p.getImage()).into(img);
//            Toast.makeText(getActivity(), "get image succeed!!", Toast.LENGTH_SHORT).show();
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

        //TODO: add swipe to refresh feature
        // Clean all elements of the recycler
        public void clear() {
            mData.clear();
            notifyDataSetChanged();
        }

        // Add a list of items -- change to type used
        public void addAll(List<HomePost> list) {
            mData.addAll(list);
            notifyDataSetChanged();
        }
    }


}