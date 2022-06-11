package com.example.firebase_chat_basic.view.fragment;

import android.content.Intent;
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
import com.example.firebase_chat_basic.view.activity.ChatRoomActivity;
import com.example.firebase_chat_basic.view.activity.MainActivity;
import com.example.firebase_chat_basic.viewModel.ChatViewModel;

// @TODO chatting list 만들기 firebase 연동.

public class ChatFragment extends Fragment {
    private FragmentChatBinding fragmentChatBinding;
    public String clientName;
    public String clientEmail;
    public String clientProfileImage;
    public String clientUID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentChatBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        getDataFromMainActivity();
        init();
        return fragmentChatBinding.getRoot();
    }

    public void init() {
        ChatViewModel chatViewModel = new ChatViewModel(clientUID);
        fragmentChatBinding.setChatViewModel(chatViewModel);
        fragmentChatBinding.setLifecycleOwner(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentChatBinding = null;
    }


    public void getDataFromMainActivity(){
        Bundle bundle = getArguments();

        if(bundle != null) {
            clientName = bundle.getString("clientName");
            clientEmail = bundle.getString("clientEmail");
            clientProfileImage = bundle.getString("clientProfileImage");
            clientUID = bundle.getString("clientUID");

            System.out.println("=============================");
            System.out.println("ChatFragment - succeeded");
            System.out.println(clientName);
            System.out.println(clientEmail);
            System.out.println(clientUID);
            System.out.println(clientProfileImage);
            System.out.println("=============================");
        }
    }
}
