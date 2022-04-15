package com.example.easysublet.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.easysublet.R;
import com.example.easysublet.databinding.ActivityMainBinding;
import com.example.easysublet.model.User;
import com.example.easysublet.viewmodel.MainViewModel;

import java.util.Locale;

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

    public static void changeLanguage(Activity activity) {
        SharedPreferences user = activity.getSharedPreferences("user" , Context.MODE_PRIVATE);
        String language = user.getString("language", "en");
        SharedPreferences.Editor edit = user.edit();
        if (language.equals("en")) {
            edit.putString("language", "zh");
        } else {
            edit.putString("language", "en");
        }
        edit.apply();
        Log.d(TAG, "changeLanguage: to" + user.getString("language", "en"));
        activity.recreate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() is called");
        setLanguage(MainActivity.this);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.loginBtn.setOnClickListener(this);
        binding.signUpBtn.setOnClickListener(this);
        binding.changeLanguageBtn.setOnClickListener(this);
        mainViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User User) {
                updateUI(User);
            }
        });

    }

    @Override
    public void onClick(View v){
        String sEmail = binding.emailEntry.getText().toString();
        String sPass = binding.passwordEntry.getText().toString();
//        Log.d(TAG, "onClick: " + sEmail + sPass);
        //TODO: for text purpose comment out above
//        String sEmail = "a123@aa.com";
//        String sPass = "1234567";

//        String sEmail = "test@1.com";
//        String sPass = "123456";

//        String sEmail = "test@4.com";
//        String sPass = "123456";
        switch (v.getId()) {
            case R.id.loginBtn:
                if (!sEmail.isEmpty() && !sPass.isEmpty()){
                    mainViewModel.setUser(sEmail , sPass);
                }
                break;

            case R.id.signUpBtn:
                Intent intent = SignUpActivity.newIntent(MainActivity.this, binding.emailEntry.getText().toString());
                startActivity(intent);
                break;

            case R.id.changeLanguageBtn:
                Log.d(TAG, "onClick: change language");
                changeLanguage(MainActivity.this);
                break;

            default:
                break;
        }
    }

    public void updateUI(User user){

        if(user != null){
            Toast.makeText(this, this.getString(R.string.login_success) ,Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, NavActivity.class));

        }else {
            Toast.makeText(this, this.getString(R.string.login_error) ,Toast.LENGTH_LONG).show();
        }

    }


}



