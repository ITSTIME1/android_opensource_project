package com.example.firebase_chat_basic.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.firebase_chat_basic.Interface.BaseInterface;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.adapters.ViewPagerAdapter;
import com.example.firebase_chat_basic.databinding.ActivityMainBinding;
import com.example.firebase_chat_basic.view.fragment.ChatFragment;
import com.example.firebase_chat_basic.view.fragment.ContactFragment;
import com.example.firebase_chat_basic.view.fragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

/**
 * [MainActivity]
 *
 * MIT License
 * Copyright (c) 2022 ITSTIME
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */



public class MainActivity extends AppCompatActivity implements BaseInterface {
    private ActivityMainBinding activityMainBinding;
    private final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private final ChatFragment chatFragment = new ChatFragment();
    private final ContactFragment contactFragment = new ContactFragment();
    private final SettingFragment settingFragment = new SettingFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initAdapter();
        initNavigationListener();
        getDataFromRegisterActivity();
    }

    // adapter init


    @Override
    public void initAdapter() {
        BaseInterface.super.initAdapter();
        fragmentArrayList.add(chatFragment);
        fragmentArrayList.add(contactFragment);
        fragmentArrayList.add(settingFragment);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, fragmentArrayList);
        activityMainBinding.viewPager.setAdapter(viewPagerAdapter);
        // 상태유지
        activityMainBinding.viewPager.setOffscreenPageLimit(fragmentArrayList.size());
    }

    // bottomNavigation click listener
    public void initNavigationListener(){
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

        activityMainBinding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if(position == 0){
                    activityMainBinding.bottomNavigation.setSelectedItemId(R.id.chat_home);
                } else if (position == 1) {
                    activityMainBinding.bottomNavigation.setSelectedItemId(R.id.chat_contact);
                } else if (position == 2) {
                    activityMainBinding.bottomNavigation.setSelectedItemId(R.id.chat_setting);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    // get data from "RegisterActivity"
    public void getDataFromRegisterActivity(){
        Intent mainActivityIntent = getIntent();

        String clientName = mainActivityIntent.getStringExtra("clientName");
        String clientEmail = mainActivityIntent.getStringExtra("clientEmail");
        String clientUID = mainActivityIntent.getStringExtra("clientUID");
        String clientProfileImage = mainActivityIntent.getStringExtra("clientProfileImage");

        System.out.println("=============================");
        System.out.println("MainActivity - succeeded");
        System.out.println(clientName);
        System.out.println(clientEmail);
        System.out.println(clientUID);
        System.out.println(clientProfileImage);
        System.out.println("=============================");



        Bundle bundle = new Bundle();
        bundle.putString("clientName", clientName);
        bundle.putString("clientEmail", clientEmail);
        bundle.putString("clientUID", clientUID);
        bundle.putString("clientProfileImage", clientProfileImage);

        chatFragment.setArguments(bundle);
    }
}