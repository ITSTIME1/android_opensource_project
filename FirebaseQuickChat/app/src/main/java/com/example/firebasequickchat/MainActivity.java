package com.example.firebasequickchat;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupMenu;

import com.example.firebasequickchat.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import Interface.StatusBarVersionCheck;
import me.ibrahimsn.lib.SmoothBottomBar;
import view.ChatFragment;
import view.ContactsFragment;
import view.SettingFragment;


/**
 * [MainActivity]
 *
 * This page contain the Contacts, Chats, Setting activity.
 * I used to [DataBinding] because I want to minimize glue code.
 * but..There's no need to worry about you don't understand.
 *
 * This project level is "middle level" because used Firebase Authentication and Firebase LiveData.
 *
 */
public class MainActivity extends AppCompatActivity implements StatusBarVersionCheck {
    private ActivityMainBinding activityMainBinding;
    private NavController navController;
    private ChatFragment chatFragment = new ChatFragment();
    private ContactsFragment contactsFragment = new ContactsFragment();
    private SettingFragment settingFragment = new SettingFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        statusBarVersionCheck();
        bottomNavigationInit();


    }

    /**
     * [BottomNavigation Initialize ]
     */
    public void bottomNavigationInit(){
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(activityMainBinding.navigationLayout.getId());
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
        /*
         * [SmoothBottomBar Setting Code for Java]
         *
         */
        SmoothBottomBar smoothBottomBar = activityMainBinding.bottomNavigation;
        PopupMenu popupMenu = new PopupMenu(this, null);
        popupMenu.inflate(R.menu.bottom_menu);
        Menu menu = popupMenu.getMenu();
        smoothBottomBar.setupWithNavController(menu, navController);
    }




    @SuppressLint("ObsoleteSdkInt")
    @Override
    public void statusBarVersionCheck() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            );
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
        }
    }

    @Override
    public void setWindowFlag(Activity activity, int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


}