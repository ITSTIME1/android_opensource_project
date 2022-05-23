package Interface;


import android.app.Activity;

/**
 * [Build Version Check Interface]
 */
public interface SystemBarBuildVersionCheck {

    // StatusBar && BottomNavigation
    void statusBarBottomNavigationBarBuildVersionCheck();

    // Only StatusBar
    void statusBarBuildVersionCheckOnlyStatusBar();

    // Only StatusBar Sub Method
    void setWindowFlag(Activity activity, final int bits, Boolean on);
}
