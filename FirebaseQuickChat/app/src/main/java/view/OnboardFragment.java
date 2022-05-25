package view;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.firebasequickchat.databinding.FragmentOnboardBinding;




public class OnboardFragment extends Fragment {

    private FragmentOnboardBinding fragmentOnboardBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentOnboardBinding = FragmentOnboardBinding.inflate(inflater, container, false);
        return fragmentOnboardBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentOnboardBinding = null;
    }
}
