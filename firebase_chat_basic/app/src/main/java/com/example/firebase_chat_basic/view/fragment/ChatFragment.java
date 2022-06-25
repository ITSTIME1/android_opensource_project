package com.example.firebase_chat_basic.view.fragment;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.firebase_chat_basic.Interface.BaseInterface;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.adapters.ChatRecyclerAdapter;
import com.example.firebase_chat_basic.databinding.FragmentChatBinding;
import com.example.firebase_chat_basic.viewModel.ChatViewModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// @TODO chatting list 만들기 firebase 연동.

public class ChatFragment extends Fragment implements BaseInterface {
    private FragmentChatBinding fragmentChatBinding;
    private ChatRecyclerAdapter chatRecyclerAdapter;
    public String getCurrentMyUID;


    @SuppressLint("NotifyDataSetChanged")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentChatBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        getDataFromMainActivity();
        defaultInit();
        return fragmentChatBinding.getRoot();
    }

    @Override
    public void defaultInit() {
        BaseInterface.super.defaultInit();
        ChatViewModel chatViewModel = new ChatViewModel(getCurrentMyUID, getActivity().getApplication());
        fragmentChatBinding.setChatViewModel(chatViewModel);
        fragmentChatBinding.setLifecycleOwner(this);

        if(chatRecyclerAdapter == null) {
            chatRecyclerAdapter = new ChatRecyclerAdapter(chatViewModel);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentChatBinding = null;
    }


    public void getDataFromMainActivity(){
        Bundle bundle = getArguments();
        if(bundle != null) {
            String clientName = bundle.getString("clientName");
            String clientEmail = bundle.getString("clientEmail");
            String clientProfileImage = bundle.getString("clientProfileImage");
            getCurrentMyUID = bundle.getString("clientUID");

            System.out.println("=============================");
            System.out.println("ChatFragment - succeeded");
            System.out.println(clientName);
            System.out.println(clientEmail);
            System.out.println(getCurrentMyUID);
            System.out.println(clientProfileImage);
            System.out.println("=============================");
        }
    }

}
