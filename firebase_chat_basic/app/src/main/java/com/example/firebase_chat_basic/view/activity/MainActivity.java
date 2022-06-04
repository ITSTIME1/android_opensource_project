package com.example.firebase_chat_basic.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.adapters.ViewPagerAdapter;
import com.example.firebase_chat_basic.databinding.ActivityMainBinding;
import com.example.firebase_chat_basic.view.fragment.ChatFragment;
import com.example.firebase_chat_basic.view.fragment.ContactFragment;
import com.example.firebase_chat_basic.view.fragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private ViewPagerAdapter viewPagerAdapter;
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private ChatFragment chatFragment = new ChatFragment();
    private ContactFragment contactFragment = new ContactFragment();
    private SettingFragment settingFragment = new SettingFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        fragmentArrayList.add(chatFragment);
        fragmentArrayList.add(contactFragment);
        fragmentArrayList.add(settingFragment);

        viewPagerAdapter = new ViewPagerAdapter(this, fragmentArrayList);

        activityMainBinding.viewPager.setAdapter(viewPagerAdapter);

        activityMainBinding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()){
                    case R.id.chat_home:
                        activityMainBinding.viewPager.setCurrentItem(0);
                        break;
                    case R.id.chat_contact:
                        activityMainBinding.viewPager.setCurrentItem(1);
                        break;
                    case R.id.chat_setting:
                        activityMainBinding.viewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });





    }

    public void onClick(View view) {
        Toast.makeText(this, "데이터 바인딩 테스트", Toast.LENGTH_SHORT).show();
    }



}