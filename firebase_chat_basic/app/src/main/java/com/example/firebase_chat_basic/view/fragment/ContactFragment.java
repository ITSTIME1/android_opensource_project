package com.example.firebase_chat_basic.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.firebase_chat_basic.Interface.BaseInterface;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.FragmentContactBinding;
import com.example.firebase_chat_basic.viewModel.ContactViewModel;

public class ContactFragment extends Fragment implements BaseInterface {
    private FragmentContactBinding fragmentContactBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentContactBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false);
        get_data_intent();
        default_init();
        return fragmentContactBinding.getRoot();
    }


    @Override
    public void default_init() {
        BaseInterface.super.default_init();
        ContactViewModel contactViewModel = new ViewModelProvider(requireActivity()).get(ContactViewModel.class);
        fragmentContactBinding.setContactViewModel(contactViewModel);
        fragmentContactBinding.setLifecycleOwner(this);

    }

    @Override
    public void get_data_intent() {
        BaseInterface.super.get_data_intent();
        Bundle bundle = getArguments();
        if (bundle != null) {

            String clientName = bundle.getString("fragment_client_name");
            String clientEmail = bundle.getString("fragment_client_email");
            String clientProfileImage = bundle.getString("fragment_client_profile_image");
            String get_current_my_uid = bundle.getString("fragment_client_uid");
            String client_phone_number = bundle.getString("fragment_client_phone_number");
            String client_online_state = bundle.getString("fragment_client_online_state");
            String client_profile_background_image = bundle.getString("fragment_client_profile_background_image");

            System.out.println("=============================");
            System.out.println("ContactFragment - succeeded");
            System.out.println(clientName);
            System.out.println(clientEmail);
            System.out.println(get_current_my_uid);
            System.out.println(clientProfileImage);
            System.out.println(client_phone_number);
            System.out.println(client_online_state);
            System.out.println(client_profile_background_image);
            System.out.println("=============================");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentContactBinding = null;
    }
}
