package com.example.easysublet.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.easysublet.databinding.ActivityAddPostBinding;
import com.example.easysublet.databinding.ActivityMyPostBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MyPostActivity extends AppCompatActivity {

    private static final String TAG = "AddPostActivity";
    private ActivityMyPostBinding binding;
    private SharedPreferences currentUser;

    public static Intent newIntent(Context packageContext, int idx){
        Intent intent = new Intent(packageContext, MyPostActivity.class);
        intent.putExtra("idx", idx);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMyPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MyHomeFragment myHomeFragment = new MyHomeFragment();
        MyRoommatesFragment myRoommatesFragment = new MyRoommatesFragment();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), 0);

        sectionsPagerAdapter.addFragment(myHomeFragment, "My home post");
        sectionsPagerAdapter.addFragment(myRoommatesFragment, "My Roommate post");

        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        int index = getIntent().getIntExtra("idx", 0);
        tabs.getTabAt(index).select();

        currentUser = getSharedPreferences("user" ,Context.MODE_PRIVATE);

    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> titles = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @Override
        public CharSequence getPageTitle (int position) {
            return titles.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    @Override
    public void finish() {
        Log.d(TAG, "finish() is called");
        Intent data = new Intent();
        setResult(RESULT_OK, data);

        super.finish();
    }
}

