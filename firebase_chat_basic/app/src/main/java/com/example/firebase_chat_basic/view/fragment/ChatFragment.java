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

import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.FragmentChatBinding;
import com.example.firebase_chat_basic.viewModel.ChatViewModel;

// @TODO chatting list 만들기 firebase 연동.

public class ChatFragment extends Fragment {
    private FragmentChatBinding fragmentChatBinding;
    private ChatViewModel chatViewModel;
    private String clientUUID;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentChatBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);

        getDataFromMainActivity();

        chatViewModel = new ChatViewModel(clientUUID);
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        fragmentChatBinding.setChatViewModel(chatViewModel);

        return fragmentChatBinding.getRoot();
    }

    public void init(){

    }



    // get data from "RegisterActivity"
    public void getDataFromMainActivity(){

        Bundle bundle = getArguments();

        if(bundle != null) {
            String clientName = bundle.getString("clientName");
            String clientEmail = bundle.getString("clientEmail");
            String clientPassword = bundle.getString("clientPassword");
            clientUUID = bundle.getString("clientUUID");

            System.out.println("=============================");
            System.out.println("ChatFragment - succeeded");
            System.out.println(clientName);
            System.out.println(clientEmail);
            System.out.println(clientPassword);
            System.out.println(clientUUID);
            System.out.println("=============================");
        }
    }
}
