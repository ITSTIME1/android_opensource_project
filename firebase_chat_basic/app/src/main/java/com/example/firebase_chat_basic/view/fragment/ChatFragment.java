package com.example.firebase_chat_basic.view.fragment;
import android.annotation.SuppressLint;
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
import com.example.firebase_chat_basic.databinding.FragmentChatBinding;
import com.example.firebase_chat_basic.viewModel.ChatViewModel;


public class ChatFragment extends Fragment implements BaseInterface {
    private FragmentChatBinding fragmentChatBinding;
    private ChatViewModel chatViewModel;


    @SuppressLint("NotifyDataSetChanged")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentChatBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        getDataIntent();
        initialize();
        intentObserver();
        return fragmentChatBinding.getRoot();
    }

    // initialize
    @Override
    public void initialize() {
        BaseInterface.super.initialize();
        chatViewModel = new ViewModelProvider(requireActivity()).get(ChatViewModel.class);
        fragmentChatBinding.setChatViewModel(chatViewModel);
        fragmentChatBinding.setLifecycleOwner(this);
    }

    @Override
    public void getDataIntent() {
        BaseInterface.super.getDataIntent();
        Bundle bundle = getArguments();
        if(bundle != null) {
            String clientName = bundle.getString("fragment_client_name");
            String clientEmail = bundle.getString("fragment_client_email");
            String clientProfileImage = bundle.getString("fragment_client_profile_image");
            String get_current_my_uid = bundle.getString("fragment_client_uid");
            String client_phone_number = bundle.getString("fragment_client_phone_number");
            String client_profile_background_image = bundle.getString("fragment_client_profile_background_image");
            String client_state_message = bundle.getString("fragment_client_state_message");
            System.out.println("=============================");
            System.out.println("ChatFragment - succeeded");

            System.out.println(clientName);
            System.out.println(clientEmail);
            System.out.println(get_current_my_uid);
            System.out.println(clientProfileImage);
            System.out.println(client_phone_number);
            System.out.println(client_profile_background_image);
            System.out.println(client_state_message);
            System.out.println("=============================");
        }
    }


    @Override
    public void intentObserver() {
        BaseInterface.super.intentObserver();
        chatViewModel.arrayListMutableLiveData.observe(getViewLifecycleOwner(), observer -> {
            // 리스트가 비어있지 않다면
            if(!observer.isEmpty()) {
                fragmentChatBinding.chatListFrameLayout.setVisibility(View.GONE);
            } else {
                fragmentChatBinding.chatListFrameLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentChatBinding = null;
    }
}
