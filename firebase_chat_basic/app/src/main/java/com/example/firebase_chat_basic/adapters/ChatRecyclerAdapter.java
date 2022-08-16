package com.example.firebase_chat_basic.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.ItemFragmentChatBinding;
import com.example.firebase_chat_basic.model.ChatListModel;
import com.example.firebase_chat_basic.view.activity.ChatRoomActivity;
import com.example.firebase_chat_basic.viewModel.ChatViewModel;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;

/**
 * [ChatRecyclerAdapter]
 *
 * <Topic>
 *     This adapter is for "ChatFragment"
 *     All data binding is consist of "ChatViewModel".
 * </Topic>
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
        holder.bind(chatViewModel, position);
    }

    @Override
    public int getItemCount() {
        if(chatViewModel.get_chat_list().size() == 0) {
            return 0;
        } else {
            return chatViewModel.get_chat_list().size();
        }
    }


    /**
     * [Custom ViewHolder]
     *
     * Class.
     *
     * 1. "ChatViewHolder"
     * 2. "ImageViewHolder"
     *
     * "ChatViewHolder viewType" is 0
     * "ImageViewHolder viewType" is 1
     *
     * Method.
     *
     * @chat_count_setting displays for "message count"
     * @chat_layout_click sends to "ChatRoomActivity"
     */


    public class CustomChatViewHolder extends RecyclerView.ViewHolder {
        ItemFragmentChatBinding itemFragmentChatBinding;

        public CustomChatViewHolder(@NonNull ItemFragmentChatBinding itemFragmentChatBinding) {
            super(itemFragmentChatBinding.getRoot());
            this.itemFragmentChatBinding = itemFragmentChatBinding;

        }

        public void bind(ChatViewModel chatViewModel, int pos) {

            // chat show animation
            Animation chatListAnimation = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.chat_recycler_anim);

            itemFragmentChatBinding.chatItemLayout.setAnimation(chatListAnimation);
            itemFragmentChatBinding.setChatViewModel(chatViewModel);
            itemFragmentChatBinding.setPos(pos);
            itemFragmentChatBinding.executePendingBindings();

            chat_count_setting(pos);
            chat_layout_click(chatViewModel, pos);
        }


        public void chat_layout_click(ChatViewModel chatViewModel, int pos){

            // This method sends five variable (name, chatKey, currentKey, otherKey, phoneNumber)
            itemFragmentChatBinding.chatItemLayout.setOnClickListener(view -> {
                Intent intoChatRoomActivity = new Intent(view.getContext(), ChatRoomActivity.class);
                intoChatRoomActivity.putExtra("getOtherName", chatViewModel.get_user_name(pos));
                intoChatRoomActivity.putExtra("getChatKey", chatViewModel.get_chat_key(pos));
                intoChatRoomActivity.putExtra("getCurrentMyUID", chatViewModel.get_my_uid(pos));
                intoChatRoomActivity.putExtra("getOtherUID", chatViewModel.get_other_uid(pos));
                intoChatRoomActivity.putExtra("getPhoneNumber", chatViewModel.get_phone_number(pos));
                view.getContext().startActivity(intoChatRoomActivity);


                int init_chat_count = Integer.parseInt(chatViewModel.get_chat_list().get(pos).getChatCount());
                if(init_chat_count > 0) {
                    chatViewModel.get_chat_list().get(pos).setChatCount("0");
                    Log.d("chat_count checking", chatViewModel.get_chat_list().get(pos).getChatCount());
                    itemFragmentChatBinding.chatCountLayout.setVisibility(View.GONE);
                }
            });
        }

        public void chat_count_setting(int pos){
            if(chatViewModel.get_chat_list().get(pos).getChatCount().isEmpty()) {
                itemFragmentChatBinding.chatCountLayout.setVisibility(View.GONE);
            } else {
                itemFragmentChatBinding.chatCountLayout.setVisibility(View.VISIBLE);
            }
        }
    }
}
