package com.example.easysublet.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import com.example.easysublet.R;
import com.example.easysublet.view.AddHomePostFragment;
import com.example.easysublet.view.AddRoommatePostFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.example.easysublet.databinding.ActivityAddPostBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddPostActivity extends AppCompatActivity {

    private static final String TAG = "AddPostActivity";
    private ActivityAddPostBinding binding;

    public static Intent newIntent(Context packageContext, int idx){
        Intent intent = new Intent(packageContext, AddPostActivity.class);
        intent.putExtra("idx", idx);
        return intent;
    }

    public static void setLanguage(Activity activity) {
        SharedPreferences user = activity.getSharedPreferences("user" , Context.MODE_PRIVATE);
        String language = user.getString("language", "en");

        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLanguage(AddPostActivity.this);
        binding = ActivityAddPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AddHomePostFragment addHomePostFragment = new AddHomePostFragment();
        AddRoommatePostFragment addRoommatePostFragment = new AddRoommatePostFragment();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), 0);

        sectionsPagerAdapter.addFragment(addHomePostFragment, getString(R.string.new_home_post));
        sectionsPagerAdapter.addFragment(addRoommatePostFragment, getString(R.string.new_roommate_post));

        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        int index = getIntent().getIntExtra("idx", 0);
        tabs.getTabAt(index).select();
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

