package view;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.firebasequickchat.databinding.ActivityIntroBinding;

/**
 * [IntroActivity]
 *
 * It show startScreen and check Firebase CurrentUser Information.
 * and then Check if currentUser is exist and he's ever seen the OnboardScreen.
 *
 * so if all is true that move on MainActivity.
 */

public class IntroActivity extends AppCompatActivity {
    private ActivityIntroBinding activityIntroBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
