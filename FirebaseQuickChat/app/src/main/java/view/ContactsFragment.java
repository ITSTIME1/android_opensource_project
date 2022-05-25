package view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.firebasequickchat.databinding.FragmentContactsBinding;

public class ContactsFragment extends Fragment {
    private FragmentContactsBinding fragmentContactsBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentContactsBinding = FragmentContactsBinding.inflate(inflater, container, false);
        return fragmentContactsBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentContactsBinding = null;
    }
}
