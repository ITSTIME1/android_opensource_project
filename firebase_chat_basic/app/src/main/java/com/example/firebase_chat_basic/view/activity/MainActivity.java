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

/*
@TODO MVVM Pattern Connect
@TODO Fragment Design
*/
public class MainActivity extends AppCompatActivity {
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

    }

    /**
     * [Adapter Initialize]
     */
    public void initAdapter(){
        fragmentArrayList.add(chatFragment);
        fragmentArrayList.add(contactFragment);
        fragmentArrayList.add(settingFragment);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, fragmentArrayList);
        activityMainBinding.viewPager.setAdapter(viewPagerAdapter);
        // 상태유지
        activityMainBinding.viewPager.setOffscreenPageLimit(fragmentArrayList.size());

    }


    /**
     * [BottomNavigation & ViewPager2 clickListener]
     */
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
}