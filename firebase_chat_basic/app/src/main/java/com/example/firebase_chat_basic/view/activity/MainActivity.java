package com.example.firebase_chat_basic.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.firebase_chat_basic.Interface.BaseInterface;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.adapters.ViewPagerAdapter;
import com.example.firebase_chat_basic.databinding.ActivityMainBinding;
import com.example.firebase_chat_basic.view.fragment.ChatFragment;
import com.example.firebase_chat_basic.view.fragment.ContactFragment;
import com.example.firebase_chat_basic.view.fragment.SettingFragment;

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
        init_adapter();
        init_navigation();
        get_data_intent();
    }

    // navigation listener init
    @SuppressLint("NonConstantResourceId")
    @Override
    public void init_navigation() {
        BaseInterface.super.init_navigation();
        activityMainBinding.bottomNavigation.setOnItemSelectedListener(item -> {
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

    @Override
    public void init_adapter() {
        BaseInterface.super.init_adapter();
        fragmentArrayList.add(chatFragment);
        fragmentArrayList.add(contactFragment);
        fragmentArrayList.add(settingFragment);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, fragmentArrayList);
        activityMainBinding.viewPager.setAdapter(viewPagerAdapter);
        // 상태유지
        activityMainBinding.viewPager.setOffscreenPageLimit(fragmentArrayList.size());
    }

    // get data from "RegisterActivity"


    @Override
    public void get_data_intent() {
        BaseInterface.super.get_data_intent();
        Intent mainActivityIntent = getIntent();


        String client_name = mainActivityIntent.getStringExtra("client_name");
        String client_email = mainActivityIntent.getStringExtra("client_email");
        String client_uid = mainActivityIntent.getStringExtra("client_uid");
        String client_profile_image = mainActivityIntent.getStringExtra("client_profile_image");
        String client_phone_number = mainActivityIntent.getStringExtra("client_phone_number");
        String client_online_state = mainActivityIntent.getStringExtra("client_online_state");
        String client_profile_background_image = mainActivityIntent.getStringExtra("client_profile_background_image");
        String client_state_message = mainActivityIntent.getStringExtra("client_state_message");

        System.out.println("=============================");
        System.out.println("MainActivity - succeeded");
        System.out.println(client_name);
        System.out.println(client_email);
        System.out.println(client_uid);
        System.out.println(client_profile_image);
        System.out.println(client_phone_number);
        System.out.println(client_online_state);
        System.out.println(client_profile_background_image);
        System.out.println(client_state_message);
        System.out.println("=============================");



        Bundle bundle = new Bundle();
        bundle.putString("fragment_client_name", client_name);
        bundle.putString("fragment_client_email", client_email);
        bundle.putString("fragment_client_uid", client_uid);
        bundle.putString("fragment_client_profile_image", client_profile_image);
        bundle.putString("fragment_client_phone_number", client_phone_number);
        bundle.putString("fragment_client_online_state", client_online_state);
        bundle.putString("fragment_client_profile_background_image", client_profile_background_image);
        bundle.putString("fragment_client_state_message", client_state_message);

        chatFragment.setArguments(bundle);
        contactFragment.setArguments(bundle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityMainBinding = null;
    }
}