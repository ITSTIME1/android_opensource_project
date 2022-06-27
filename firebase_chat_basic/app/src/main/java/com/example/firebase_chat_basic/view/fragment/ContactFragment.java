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

import java.util.Objects;

public class ContactFragment extends Fragment implements BaseInterface {
    private FragmentContactBinding fragmentContactBinding;
    private String getCurrentMyUID;
    private ContactViewModel contactViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentContactBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false);
        getDataFromActivity();
        defaultInit();
        return fragmentContactBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentContactBinding = null;
    }

    @Override
    public void defaultInit() {
        BaseInterface.super.defaultInit();
        contactViewModel = new ContactViewModel(requireActivity().getApplication(), getCurrentMyUID);
        fragmentContactBinding.setContactViewModel(contactViewModel);
        fragmentContactBinding.setLifecycleOwner(this);

    }

    @Override
    public void getDataFromActivity() {
        BaseInterface.super.getDataFromActivity();
        Bundle bundle = getArguments();
        if (bundle != null) {

            String clientName = bundle.getString("fragment_client_name");
            String clientEmail = bundle.getString("fragment_client_email");
            String clientProfileImage = bundle.getString("fragment_client_profile_image");
            getCurrentMyUID = bundle.getString("fragment_client_uid");
            String client_phone_number = bundle.getString("fragment_client_phone_number");

            System.out.println("=============================");
            System.out.println("ContactFragment - succeeded");
            System.out.println(clientName);
            System.out.println(clientEmail);
            System.out.println(getCurrentMyUID);
            System.out.println(clientProfileImage);
            System.out.println(client_phone_number);
            System.out.println("=============================");
        }
    }
}
