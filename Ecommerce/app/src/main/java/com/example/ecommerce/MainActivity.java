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

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {


    FrameLayout home_ly;
    BottomNavigationView bottomNavigationView;
    OrderCartFragment orderCartFragment = new OrderCartFragment();
    CategoryFragment categoryFragment = new CategoryFragment();
    MyProfileFragment myProfileFragment = new MyProfileFragment();
    HomeFragment homeFragment = new HomeFragment();
    SearchFragment searchFragment = new SearchFragment();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Object Reference
        init();

        // BottomNavigation listener
        bottomNavigationView.setOnItemSelectedListener(this);

    }




    // Reference
    private void init(){
        home_ly = findViewById(R.id.home_ly);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }




    // FragmentManager Change Fragment Method
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.home_ly, fragment);
        fragmentTransaction.commit();
    }


    // OnNavigationItem Listener Method Override
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
