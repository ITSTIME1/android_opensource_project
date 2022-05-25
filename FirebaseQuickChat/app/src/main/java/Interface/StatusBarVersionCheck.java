package Interface;

import android.app.Activity;

public interface StatusBarVersionCheck {
    public void statusBarVersionCheck();
    public void setWindowFlag(Activity activity, final int bits, boolean on);
}
