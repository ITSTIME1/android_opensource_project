package com.example.firebase_chat_basic.view.fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.FragmentSettingBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SettingFragment extends Fragment {
    private FragmentSettingBinding fragmentSettingBinding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentSettingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);
        /*
         * Do this
         */
        return fragmentSettingBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentSettingBinding = null;
    }
}
