package com.example.easysublet.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.easysublet.R;
import com.example.easysublet.databinding.ActivitySignUpBinding;
import com.example.easysublet.model.User;
import com.example.easysublet.repository.helperRepo;
import com.example.easysublet.viewmodel.SignUpViewModel;

import java.util.Locale;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "SignUpActivity";
    private String message = "Please sign in";
    private static final int REQUEST_CODE_HOME = 0;

    private SignUpViewModel signUpViewModel;
    private ActivitySignUpBinding binding;

    public static Intent newIntent(Context packageContext, String email){
        Intent intent = new Intent(packageContext, SignUpActivity.class);
        intent.putExtra("email", email);
        return  intent;
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
        Log.d(TAG, "onCreate() is called");
        setLanguage(SignUpActivity.this);
        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.emailEntry.setText(getIntent().getStringExtra("email"));
        binding.signUpBtn.setOnClickListener(this);

        signUpViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBtn:
                if(helperRepo.isConnected(getApplication())){
                    if (binding.passwordEntry != null && binding.emailEntry != null && binding.usernameEntry != null) {
                        if (!binding.passwordEntry.getText().toString().equals(binding.confirmPasswordEntry.getText().toString())) {
                            Toast.makeText(SignUpActivity.this, "Two passwords are different", Toast.LENGTH_SHORT).show();
                            binding.passwordEntry.setText(null);
                            binding.confirmPasswordEntry.setText(null);
                        } else {
                            signUpViewModel.addUser(binding.usernameEntry.getText().toString(), binding.emailEntry.getText().toString(), binding.passwordEntry.getText().toString());
                            Toast.makeText(SignUpActivity.this, R.string.register_succeeded, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void finish() {
        Log.d(TAG, "finish() is called");
        Intent data = new Intent();
        data.putExtra("message", message);
        data.putExtra("email", binding.emailEntry.getText().toString());
        setResult(RESULT_OK, data);

        super.finish();
    }

}