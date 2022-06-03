package com.example.firebase_chat_basic.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setMainActivity(this);

    }
    public void onClick(View view){
        Toast.makeText(this, "데이터 바인딩 테스트", Toast.LENGTH_SHORT).show();
    }
}