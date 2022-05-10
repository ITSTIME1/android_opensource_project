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


// ** MainActivity **
// This Activity is connecting activity that meaning is bottomNavigation and fragment or other activity
// if you want to add reference you have to include in this activity


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


        // ** Object Reference Method **
        // This method that refers all objects in "MainActivity"
        init();

        // ** BottomNavigation listener **
        // This method is a change method that meaning is when you clicked bottomNavigationItem
        // change fragment what you want
        bottomNavigationView.setOnItemSelectedListener(this);

        // ** Automatically default set fragment == HomeFragment **
        if(savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.tab_home);
        }

    }




    // ** All object reference method **
    private void init(){
        home_ly = findViewById(R.id.home_ly);
        orderCartFragment = new OrderCartFragment();
        categoryFragment = new CategoryFragment();
        myProfileFragment = new MyProfileFragment();
        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }




    // ** ReplaceFragment Method **
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.home_ly, fragment);
        fragmentTransaction.commit();
    }


    // ** OnBottomNavigationItem override listener method **
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
