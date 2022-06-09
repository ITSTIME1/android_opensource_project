package com.example.firebase_chat_basic.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.firebase_chat_basic.databinding.ItemFragmentChatBinding;
import com.example.firebase_chat_basic.viewModel.ChatViewModel;


public class ChatRecyclerAdapter extends RecyclerView.Adapter<ChatRecyclerAdapter.CustomChatViewHolder> {
    private final ChatViewModel chatViewModel;

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
        return chatViewModel.getChatListModelList() == null ?
                0 : chatViewModel.getChatListModelList().size();

    }

    public class CustomChatViewHolder extends RecyclerView.ViewHolder {
        ItemFragmentChatBinding itemFragmentChatBinding;

        public CustomChatViewHolder(@NonNull ItemFragmentChatBinding itemFragmentChatBinding) {
            super(itemFragmentChatBinding.getRoot());
            this.itemFragmentChatBinding = itemFragmentChatBinding;
        }

        public void bind(ChatViewModel chatViewModel, int pos){
            itemFragmentChatBinding.setChatViewModel(chatViewModel);
            itemFragmentChatBinding.setPos(pos);
            itemFragmentChatBinding.executePendingBindings();
        }
    }
}
