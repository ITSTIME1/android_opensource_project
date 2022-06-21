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
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.adapters.ChatRecyclerAdapter;
import com.example.firebase_chat_basic.databinding.FragmentChatBinding;
import com.example.firebase_chat_basic.viewModel.ChatViewModel;

// @TODO chatting list 만들기 firebase 연동.

public class ChatFragment extends Fragment {
    private FragmentChatBinding fragmentChatBinding;
    private ChatRecyclerAdapter chatRecyclerAdapter;
    private ChatViewModel chatViewModel;
    public String clientName;
    public String clientEmail;
    public String clientProfileImage;
    public String getCurrentMyUID;


    @SuppressLint("NotifyDataSetChanged")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentChatBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        getDataFromMainActivity();
        init();
        return fragmentChatBinding.getRoot();
    }

    public void init() {
        chatViewModel = new ChatViewModel(getCurrentMyUID, getActivity().getApplication());
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
            clientName = bundle.getString("clientName");
            clientEmail = bundle.getString("clientEmail");
            clientProfileImage = bundle.getString("clientProfileImage");
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
