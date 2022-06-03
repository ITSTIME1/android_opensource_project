package com.example.firebase_chat_basic.viewModel;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.firebase_chat_basic.adapters.ViewPagerAdapter;
import com.example.firebase_chat_basic.view.fragment.ChatFragment;
import com.example.firebase_chat_basic.view.fragment.ContactFragment;
import com.example.firebase_chat_basic.view.fragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {
    private FragmentStateAdapter fragmentStateAdapter;
    private NavigationBarView.OnItemSelectedListener onItemSelectedListener;
    private FragmentManager fragmentManager;
    private Context context;
    public MainViewModel(FragmentStateAdapter fragmentStateAdapter, NavigationBarView.OnItemSelectedListener onItemSelectedListener) {
        this.fragmentStateAdapter = fragmentStateAdapter;
        this.onItemSelectedListener = onItemSelectedListener;


        ArrayList<Fragment> fragmentArrayList = new ArrayList<Fragment>();

        fragmentArrayList.add(new ChatFragment());
        fragmentArrayList.add(new ContactFragment());
        fragmentArrayList.add(new SettingFragment());

        fragmentStateAdapter = new ViewPagerAdapter(fragmentManager, context);

    }

}
