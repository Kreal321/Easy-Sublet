package com.example.easysublet.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.easysublet.R;
import com.example.easysublet.databinding.ActivitySettingBinding;
import com.example.easysublet.viewmodel.SettingViewModel;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SettingActivity";

    private SettingViewModel settingViewModel;
    private ActivitySettingBinding binding;
    private FragmentManager fm;
    private Fragment fragment;

    public static Intent newIntent(Context packageContext, String email){
        Intent intent = new Intent(packageContext, SettingActivity.class);
        intent.putExtra("email", email);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() is called");

        settingViewModel = new ViewModelProvider(this).get(SettingViewModel.class);

        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        SharedPreferences emailStored = getSharedPreferences("email",Context.MODE_PRIVATE);
        String email = emailStored.getString("email",null);
        settingViewModel.setUser(email);

        binding.changePwBtn.setOnClickListener(this);
        binding.deleteAccountBtn.setOnClickListener(this);
        binding.logoutBtn.setOnClickListener(this);

        fm = getSupportFragmentManager();
        fragment = (Fragment) fm.findFragmentById(R.id.fragment_container_view);

        /** snip **/
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("ACTION_LOGOUT");
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG,"Logout in progress");
                finish();
            }
        }, intentFilter);
        //** snip **//
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changePwBtn:
//                FragmentManager fm = getSupportFragmentManager();
//                Fragment fragment = (Fragment) fm.findFragmentById(R.id.fragment_container_view);
//                Bundle bundle = new Bundle();
                if (fragment == null) {
                    fragment = new ChangePasswordFragment();
                    fm.beginTransaction()
                            .setReorderingAllowed(true)
                            .add(R.id.fragment_container_view, fragment,null)
                            .commit();
                }

                break;

            case R.id.deleteAccountBtn:
                settingViewModel.deleteUser();
                break;

            case R.id.logoutBtn:
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction("ACTION_LOGOUT");
                sendBroadcast(broadcastIntent);

                break;

            default:
                break;
        }
    }
}