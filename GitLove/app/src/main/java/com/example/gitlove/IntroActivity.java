package com.example.gitlove;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import Interface.SystemBarBuildVersionCheck;


/**
 * [SplashScreen]
 *
 * I use "custom splashscreen" because when it excute not showing AnimatedIcon.
 * so I found it's problem android studio so Google might be investigation.
 */
public class IntroActivity extends AppCompatActivity implements SystemBarBuildVersionCheck {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.gitlove.databinding.ActivityIntroBinding activityIntroBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_intro);

        statusBarBuildVersionCheck();

        /*
         * [Firebase LoginUser Check]
         *
         * It need to check firebase current user information.
         *
         */
        startLoading();

    }


    /**
     * [StatusBar VersionCheck]
     *
     * It's want to change "status bar color" completely transparent.
     */
    @SuppressLint("ObsoleteSdkInt")
    public void statusBarBuildVersionCheck(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }


    @Override
    public void statusBarBottomNavigationBarBuildVersionCheck() {

    }

    @Override
    public void statusBarBuildVersionCheckOnlyStatusBar() {

    }

    @Override
    public void setWindowFlag(Activity activity, int bits, Boolean on) {
    }


    /**
     * [StartLoading]
     *
     * It's here you want to need thing add business logic.
     */
    private void startLoading() {
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 2000);
    }
}
