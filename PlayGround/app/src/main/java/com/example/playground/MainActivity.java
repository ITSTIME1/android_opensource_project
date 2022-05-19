package com.example.playground;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.example.playground.databinding.ActivityMainBinding;

import Model.Test;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private final Test test = new Test();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.
    }
}