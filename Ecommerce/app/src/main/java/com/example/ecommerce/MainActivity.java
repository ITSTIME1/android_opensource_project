package com.example.ecommerce;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.google.android.material.navigation.NavigationBarView;

import Fragments.CategoryFragment;
import Fragments.HomeFragment;
import Fragments.MyProfileFragment;
import Fragments.OrderCartFragment;
import Fragments.SearchFragment;

/**
 * [MainActivity]
 *
 * This is very important activity
 * it has navigationView and Fragment so main acitivty is main "Lifecycle"
 * and I'm used to FragmentManager that because I need to change content of fragment.
 *
 * If you want to know how to working navigationView change so If then, you need to check [replaceFragment]
 * */


public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {


    private FrameLayout home_ly;
    private BottomNavigationView bottomNavigationView;
    private OrderCartFragment orderCartFragment;
    private CategoryFragment categoryFragment;
    private MyProfileFragment myProfileFragment;
    private HomeFragment homeFragment;
    private SearchFragment searchFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /**
         * [Init Method]
         *
         * That's reference method.
         */
        init();

        /**
         * [Bottom Navigation ItemClickListener]
         *
         * I'm using implementation NavigationBarView.OnItemSelectedListener
         * because I don't want complicated code and very simple
         * if other solution I think make a OnitemSelectdListener class.
         * but this project used to first solution.
         */
        bottomNavigationView.setOnItemSelectedListener(this);

        // ** Automatically default set fragment == HomeFragment **
        if(savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.tab_home);
        }

    }



    private void init(){
        home_ly = findViewById(R.id.home_ly);
        orderCartFragment = new OrderCartFragment();
        categoryFragment = new CategoryFragment();
        myProfileFragment = new MyProfileFragment();
        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }




    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.home_ly, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tab_order:
                replaceFragment(orderCartFragment);
                return true;
            case R.id.tab_category:
                replaceFragment(categoryFragment);
                return true;
            case R.id.tab_search:
                replaceFragment(searchFragment);
                return true;
            case R.id.tab_home:
                replaceFragment(homeFragment);
                return true;
            case R.id.tab_profile:
                replaceFragment(myProfileFragment);
                return true;
        }
        return true;
    }
}
