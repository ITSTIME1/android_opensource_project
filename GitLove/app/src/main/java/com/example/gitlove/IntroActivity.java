package com.example.gitlove;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * [SplashScreen]
 *
 * I use "custom splashscreen" because when it excute not showing AnimatedIcon.
 * so I found it's problem android studio so Google might be investigation.
 */
public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        StatusBarBuildVersionCheck();

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
    private void StatusBarBuildVersionCheck(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
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
