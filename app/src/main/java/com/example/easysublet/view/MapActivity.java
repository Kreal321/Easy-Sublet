package com.example.easysublet.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.easysublet.databinding.ActivityMapBinding;
import com.example.easysublet.viewmodel.MapViewModel;

public class MapActivity extends AppCompatActivity {

    private static final String TAG = "MapActivity";
    private ActivityMapBinding binding;
    private MapViewModel mapViewModel;

    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, MapActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);
        mapViewModel.getText().observe(this, binding.text::setText);

    }
}