package com.example.firebase_chat_basic.view.fragment;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.firebase_chat_basic.view.activity.SplashActivity;
import com.example.firebase_chat_basic.viewModel.ChatViewModel;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;

import java.util.ArrayList;


public class ChatFragment extends Fragment implements BaseInterface {
    private FragmentChatBinding fragmentChatBinding;
    private ChatRecyclerAdapter chat_recycler_adapter;
    private ChatViewModel chatViewModel;
    public String get_current_my_uid;
    @SuppressLint("NotifyDataSetChanged")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentChatBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        get_intent_data();
        default_init();
        observer_intent();
        return fragmentChatBinding.getRoot();
    }

    // initialize
    @Override
    public void default_init() {
        BaseInterface.super.default_init();
        chatViewModel = new ChatViewModel(get_current_my_uid, requireActivity().getApplication());
        fragmentChatBinding.setChatViewModel(chatViewModel);
        fragmentChatBinding.setLifecycleOwner(this);
        if(chat_recycler_adapter == null) {
            chat_recycler_adapter = new ChatRecyclerAdapter(chatViewModel);
        }
    }


    // get intent data
    public void get_intent_data(){
        Bundle bundle = getArguments();
        if(bundle != null) {
            String clientName = bundle.getString("fragment_client_name");
            String clientEmail = bundle.getString("fragment_client_email");
            String clientProfileImage = bundle.getString("fragment_client_profile_image");
            get_current_my_uid = bundle.getString("fragment_client_uid");
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

    // destroy fragment chat binding view lifecycle
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    // liveData 를 관찰하고 있다가 만약 데이터가 다 삽입 되었으면
    // shimmerLayout 을 멈추고
    // 보여주지 않는다.
    @Override
    public void observer_intent() {
        BaseInterface.super.observer_intent();
        chatViewModel.arrayListMutableLiveData.observe(getViewLifecycleOwner(), liveData -> {
            // 데이터가 비어있지 않다면 즉 값이 있다면 shimmer 뷰를 그만 보여준다.
            if(!liveData.isEmpty()) {
                fragmentChatBinding.chatNotingFrameLayout.setVisibility(View.GONE);
                fragmentChatBinding.chatShimmerLayout.stopShimmer();
                fragmentChatBinding.chatShimmerLayout.setVisibility(View.GONE);
                Log.d("shimmer effect stop ", "stop shimmer");
            }
        });
    }
}
