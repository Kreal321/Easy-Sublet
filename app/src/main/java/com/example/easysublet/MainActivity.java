package com.example.easysublet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE_HOME = 0;
    private EditText mEmail;
    private EditText mPassword;
    private Button mSignInButton;

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


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate() is called");

        // Initialize
        mEmail = (EditText) findViewById(R.id.emailEntry);
        mPassword = (EditText) findViewById(R.id.passwordEntry);

        if (savedInstanceState != null) {
            mEmail.setText(savedInstanceState.getString("email"));
            mPassword.setText(savedInstanceState.getString("password"));
        }

        Toast.makeText(getApplicationContext(), "1st call onCreate()", Toast.LENGTH_LONG).show();


    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), "Invoked onStart()", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onStart() is called");



        mSignInButton = (Button) findViewById(R.id.loginBtn);

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEmail.getText().toString().equals("a") && mPassword.getText().toString().equals("a")){
                    Toast.makeText(MainActivity.this,"success", Toast.LENGTH_SHORT).show();
                    Intent intent = HomeActivity.newIntent(MainActivity.this, "User");
                    startActivityForResult(intent, REQUEST_CODE_HOME);
                } else {
                    Toast.makeText(MainActivity.this,"fail", Toast.LENGTH_SHORT).show();
                }

            }

        });
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
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putString("email", mEmail.getText().toString());
        savedInstanceState.putString("password", mPassword.getText().toString());
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



