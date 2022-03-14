package com.example.easysublet.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;

import com.example.easysublet.R;
import com.example.easysublet.databinding.ActivityMainBinding;
import com.example.easysublet.model.User;
import com.example.easysublet.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE_HOME = 0;
    private static final int REQUEST_CODE_SIGNUP = 1;

    private MainViewModel mainViewModel;
    private ActivityMainBinding binding;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult() is called");

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_HOME){
            if (data == null) {
                return;
            }
            Toast.makeText(getApplicationContext(), data.getStringExtra("message"), Toast.LENGTH_LONG).show();
        }

        if (requestCode == REQUEST_CODE_SIGNUP){
            if (data == null) {
                return;
            }
            binding.emailEntry.setText(data.getStringExtra("email"));
            Toast.makeText(getApplicationContext(), data.getStringExtra("message"), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() is called");

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        binding.loginBtn.setOnClickListener(this);
        binding.signUpBtn.setOnClickListener(this);

        mainViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {

            }
        });

    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.loginBtn:
                if (mainViewModel.setUser(binding.emailEntry.getText().toString(), binding.passwordEntry.getText().toString())){
                    Toast.makeText(MainActivity.this,"success", Toast.LENGTH_SHORT).show();
                    Intent intent = HomeActivity.newIntent(MainActivity.this, "User");
                    startActivityForResult(intent, REQUEST_CODE_HOME);
                } else {
                    Toast.makeText(MainActivity.this,"fail", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.signUpBtn:
                Intent intent = SignUpActivity.newIntent(MainActivity.this, binding.emailEntry.getText().toString());
                startActivityForResult(intent, REQUEST_CODE_SIGNUP);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() is called");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() is called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() is called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() is called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() is called");
    }

}



