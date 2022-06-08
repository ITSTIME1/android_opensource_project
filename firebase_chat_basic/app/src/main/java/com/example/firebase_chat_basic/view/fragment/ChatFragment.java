package com.example.firebase_chat_basic.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.FragmentChatBinding;


public class ChatFragment extends Fragment {
    private FragmentChatBinding fragmentChatBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentChatBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        getData();
        return fragmentChatBinding.getRoot();
    }



    // get data from "RegisterActivity"
    public void getData(){

        Bundle bundle = getArguments();

        if(bundle != null) {
            String clientName = bundle.getString("clientName");
            String clientEmail = bundle.getString("clientEmail");
            String clientPassword = bundle.getString("clientPassword");

            System.out.println("=============================");
            System.out.println("ChatFragment - succeeded");
            System.out.println(clientName);
            System.out.println(clientEmail);
            System.out.println(clientPassword);
            System.out.println("=============================");
        }
    }
}
