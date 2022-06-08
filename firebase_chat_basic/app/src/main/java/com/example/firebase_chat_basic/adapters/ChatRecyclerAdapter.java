package com.example.firebase_chat_basic.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase_chat_basic.databinding.ItemFragmentChatBinding;
import com.example.firebase_chat_basic.viewModel.ChatViewModel;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<ChatRecyclerAdapter.CustomChatViewHolder> {
    private ChatViewModel chatViewModel;

    public ChatRecyclerAdapter(ChatViewModel chatViewModel) {
        this.chatViewModel = chatViewModel;
    }

    @NonNull
    @Override
    public ChatRecyclerAdapter.CustomChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFragmentChatBinding itemFragmentChatBinding = ItemFragmentChatBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CustomChatViewHolder(itemFragmentChatBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRecyclerAdapter.CustomChatViewHolder holder, int position) {
        // 데이터를 연결해줌.
        holder.bind(chatViewModel, position);

    }

    @Override
    public int getItemCount() {
        if(chatViewModel.getChatListModelArrayList() == null) {
            return 0;
        } else {
            return chatViewModel.getChatListModelArrayList().size();
        }
    }

    public class CustomChatViewHolder extends RecyclerView.ViewHolder {
        ItemFragmentChatBinding itemFragmentChatBinding;

        public CustomChatViewHolder(@NonNull ItemFragmentChatBinding itemFragmentChatBinding) {
            super(itemFragmentChatBinding.getRoot());
            this.itemFragmentChatBinding = itemFragmentChatBinding;
        }

        public void bind(ChatViewModel chatViewModel, int pos){
            // XMl 에서 chatViewModel 을 사용해서 binding 하겠다고 설정.
            itemFragmentChatBinding.setItemChatViewModel(chatViewModel);
            // pos 는 integer 형으로 받아올 값들은 인덱스 값이기 때문에 integer를 사용함.
            itemFragmentChatBinding.setPos(pos);
            itemFragmentChatBinding.executePendingBindings();
        }
    }
}
