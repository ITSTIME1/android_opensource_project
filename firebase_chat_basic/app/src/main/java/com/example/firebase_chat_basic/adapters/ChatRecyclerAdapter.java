package com.example.firebase_chat_basic.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase_chat_basic.databinding.ItemFragmentChatBinding;
import com.example.firebase_chat_basic.model.ChatListModel;
import com.example.firebase_chat_basic.view.activity.ChatRoomActivity;
import com.example.firebase_chat_basic.viewModel.ChatViewModel;

/**
 * [ChatRecyclerAdapter]
 * <p>
 * This adapter is that for "ChatFragment"
 * All data binding is consist of "ChatViewModel".
 */

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
        // connect to in chatViewModel method
        holder.bind(chatViewModel, position);

    }

    @Override
    public int getItemCount() {
        // return the method(getChatListModelList) in the chatViewModel
        if(chatViewModel.get_chat_list().size() == 0) {
            return 0;
        } else {
            return chatViewModel.get_chat_list().size();
        }
    }


    // custom "ChatViewHolder"
    // ItemFragmentChatBinding == item_fragment_chat

    public class CustomChatViewHolder extends RecyclerView.ViewHolder {
        ItemFragmentChatBinding itemFragmentChatBinding;

        public CustomChatViewHolder(@NonNull ItemFragmentChatBinding itemFragmentChatBinding) {
            super(itemFragmentChatBinding.getRoot());
            this.itemFragmentChatBinding = itemFragmentChatBinding;

        }

        public void bind(ChatViewModel chatViewModel, int pos) {
            itemFragmentChatBinding.setChatViewModel(chatViewModel);
            itemFragmentChatBinding.setPos(pos);
            itemFragmentChatBinding.executePendingBindings();


            // get_chat_list 의 position 값의 chatcount 값을 가져와서 그 chatcount 없다면 보이지 않게 한다.
            // get_chat_list 의 position 값의 chatcount 값을 가져와서 만약 그 chatcount 값이 0 이상이라면 채팅이 있기 때문에 카운트를 표시해준다.
            chat_count_setting(pos);
            chat_layout_click(chatViewModel, pos);
        }


        // chat_layout_click method
        public void chat_layout_click(ChatViewModel chatViewModel, int pos){
            // 이름, uid, 그 사람으 ud
            itemFragmentChatBinding.chatItemLayout.setOnClickListener(view -> {
                Intent intoChatRoomActivity = new Intent(view.getContext(), ChatRoomActivity.class);
                intoChatRoomActivity.putExtra("getOtherName", chatViewModel.get_user_name(pos));
                intoChatRoomActivity.putExtra("getChatKey", chatViewModel.get_chat_key(pos));
                intoChatRoomActivity.putExtra("getCurrentMyUID", chatViewModel.get_my_uid(pos));
                intoChatRoomActivity.putExtra("getOtherUID", chatViewModel.get_other_uid(pos));
                view.getContext().startActivity(intoChatRoomActivity);

                // 채팅을 눌렀을 때 count 가 0 보다 크다면 채팅 값이 있다는 거니까 클릭할 때 마다 초기화
                // 채팅을 눌렀을 때 count 가 0 보다 작다면
                // 그 count 를 0으로 초기화

                // 채팅이 다시 온다면 text 값에는 다시 총 개수를 반환
                int init_chat_count = Integer.parseInt(chatViewModel.get_chat_list().get(pos).getChatCount());
                if(init_chat_count > 0) {
                    chatViewModel.get_chat_list().get(pos).setChatCount("0");
                    Log.d("chat_count 확인 해봅시다 ", chatViewModel.get_chat_list().get(pos).getChatCount());
                    itemFragmentChatBinding.chatCountLayout.setVisibility(View.GONE);
                    // 여기 까지 클릭해서 로직이 돌게 된다면 현재 chat_count는 0이에요
                    // 근데 만약 상대방이 나에게 채팅을 보낸다면 거기서 로직이 다시 돌게 됩니다.
                }
            });
        }

        // chat_count_setting method
        public void chat_count_setting(int pos){
            if(chatViewModel.get_chat_list().get(pos).getChatCount().isEmpty()) {
                itemFragmentChatBinding.chatCountLayout.setVisibility(View.GONE);
            } else {
                itemFragmentChatBinding.chatCountLayout.setVisibility(View.VISIBLE);
            }
        }
    }
}
