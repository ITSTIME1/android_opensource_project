package com.example.firebase_chat_basic.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.ItemMessageBinding;
import com.example.firebase_chat_basic.model.ChatRoomModel;
import com.example.firebase_chat_basic.viewModel.ChatViewModel;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.Iterables;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

public class ChatRoomRecyclerAdapter extends RecyclerView.Adapter<ChatRoomRecyclerAdapter.CustomViewHolder> {
    private final SharedPreferences sharedPreferences;
    private final ArrayList<ChatRoomModel> chatRoomModelArrayList;

    public ChatRoomRecyclerAdapter(ArrayList<ChatRoomModel> chatRoomModelArrayList, Context context) {
        this.chatRoomModelArrayList = chatRoomModelArrayList;
        sharedPreferences = context.getSharedPreferences("authentication", Activity.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ChatRoomRecyclerAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMessageBinding itemMessageBinding = ItemMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CustomViewHolder(itemMessageBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomRecyclerAdapter.CustomViewHolder holder, int position) {
        Animation myChatAnimation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.chat_room_message_my_anim);
        Animation otherChatAnimation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.chat_room_message_other_anim);


        final String get_key = chatRoomModelArrayList.get(position).getSetKey();
        // [ p - p chat show logic ]
        // @TODO 이전 날짜랑 비교 했을 때 이전 날짜가 더 작고 마지막 값이라면 그때의 마지막 날짜를 표시해준다.
        // @TODO 마지막 채팅이 아니라면 layout을 다른걸로 보여준다.
        if(get_key.equals(sharedPreferences.getString("authentication_uid", ""))) {
            holder.itemMessageBinding.myMessageLayout.setVisibility(View.VISIBLE);
            holder.itemMessageBinding.otherMessageLayout.setVisibility(View.GONE);
            holder.itemMessageBinding.myMessageText.setText(chatRoomModelArrayList.get(position).getChat_message());
            // 마지막 값이라면 시간을 보여주고
            // 마지막 값이 아니라면 보여주지 않는다.
            if(!(chatRoomModelArrayList.get(position) ==  Iterables.getLast(chatRoomModelArrayList))) {
                holder.itemMessageBinding.myMessageDate.setVisibility(View.GONE);
            } else {
                // animation 적용
                holder.itemMessageBinding.myMessageLayout.setAnimation(myChatAnimation);
                holder.itemMessageBinding.myMessageDate.setVisibility(View.VISIBLE);
                holder.itemMessageBinding.myMessageDate.setText(chatRoomModelArrayList.get(position).getChat_date());
            }
        } else {
            holder.itemMessageBinding.myMessageLayout.setVisibility(View.GONE);
            holder.itemMessageBinding.otherMessageLayout.setVisibility(View.VISIBLE);
            holder.itemMessageBinding.otherMessageText.setText(chatRoomModelArrayList.get(position).getChat_message());
            if(!(chatRoomModelArrayList.get(position) ==  Iterables.getLast(chatRoomModelArrayList))) {
                holder.itemMessageBinding.otherMessageDate.setVisibility(View.GONE);
            } else {
                holder.itemMessageBinding.myMessageLayout.setAnimation(otherChatAnimation);
                holder.itemMessageBinding.otherMessageDate.setVisibility(View.VISIBLE);
                holder.itemMessageBinding.otherMessageDate.setText(chatRoomModelArrayList.get(position).getChat_date());
            }
        }
    }
    @Override
    public int getItemCount() {
        if(chatRoomModelArrayList.size() == 0) {
            return 0;
        } else {
            return chatRoomModelArrayList.size();
        }

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ItemMessageBinding itemMessageBinding;

        public CustomViewHolder(@NonNull ItemMessageBinding itemMessageBinding) {
            super(itemMessageBinding.getRoot());
            this.itemMessageBinding = itemMessageBinding;
        }
    }
}
