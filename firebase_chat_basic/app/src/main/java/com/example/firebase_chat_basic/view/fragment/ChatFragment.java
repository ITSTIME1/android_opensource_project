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
import com.example.firebase_chat_basic.viewModel.ChatViewModel;

/**
 * @TODO ChatModel need to implementation "DATE" and "Alert Count"
 * @TODO Contacts "RecyclerView" and "Adapter" and "Contact ItemModel & Item"
 * @TODO Setting Fragment
 */
public class ChatFragment extends Fragment {
    private FragmentChatBinding fragmentChatBinding;
    private ChatViewModel chatViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentChatBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        init();
        return fragmentChatBinding.getRoot();
    }

    public void init(){
        chatViewModel = new ChatViewModel();
        chatViewModel.onCreate();

        fragmentChatBinding.setChatFragmentViewModel(chatViewModel);
        fragmentChatBinding.setLifecycleOwner(this);
    }
}
