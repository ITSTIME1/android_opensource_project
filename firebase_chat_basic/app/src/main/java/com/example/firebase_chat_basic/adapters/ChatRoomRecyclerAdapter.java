package com.example.firebase_chat_basic.adapters;

;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase_chat_basic.databinding.ItemMessageBinding;
import com.example.firebase_chat_basic.model.ChatRoomModel;
import com.example.firebase_chat_basic.viewModel.ChatViewModel;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class ChatRoomRecyclerAdapter extends RecyclerView.Adapter<ChatRoomRecyclerAdapter.CustomViewHolder> {
    private final SharedPreferences sharedPreferences;
    private ArrayList<ChatRoomModel> chatRoomModelArrayList;
    private Context context;

    public ChatRoomRecyclerAdapter(ArrayList<ChatRoomModel> chatRoomModelArrayList, Context context) {
        this.chatRoomModelArrayList = chatRoomModelArrayList;
        this.context = context;
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
        final String get_key = chatRoomModelArrayList.get(position).getSetKey();
        // 키 값이 나의 키 값이랑 같으면 my text, my date 에 적용

        // 1. 만약 처음 보낸 메세지라면 현재 데이트를 보여준다.
        // 2. 만약 이전 데이트 값이랑 비교했을 때 같지 않다면 보여준다.
        // 3. 처음 보낸 메세지가 아니라면 전 채팅의 데이트 값이랑 비교해서 다르다면 보여준다.
        // 4. 만약 이전 데이트 값이 null 이라면 값이 없다면 첫 데이터라는 거니까 보여준다.

        // date show function complete.
        if(chatRoomModelArrayList.get(position) == chatRoomModelArrayList.get(0)) {
            holder.itemMessageBinding.myMessageTopDate.setVisibility(View.VISIBLE);
            holder.itemMessageBinding.myMessageTopDate.setText(chatRoomModelArrayList.get(position).getCurrent_date());
        } else if(Objects.equals(chatRoomModelArrayList.get(position - 1).getCurrent_date(), chatRoomModelArrayList.get(position).getChat_date())) {
            holder.itemMessageBinding.myMessageTopDate.setVisibility(View.GONE);
        } else if(chatRoomModelArrayList.get(position - 1).getCurrent_date() == null) {
            holder.itemMessageBinding.myMessageTopDate.setVisibility(View.VISIBLE);
            holder.itemMessageBinding.myMessageTopDate.setText(chatRoomModelArrayList.get(position).getCurrent_date());
        }
        else {
            if(Objects.equals(chatRoomModelArrayList.get(position - 1).getCurrent_date(), chatRoomModelArrayList.get(position).getCurrent_date())) {
                holder.itemMessageBinding.myMessageTopDate.setVisibility(View.GONE);
            }
        }

        if(get_key.equals(sharedPreferences.getString("authentication_uid", ""))) {
            holder.itemMessageBinding.myMessageLayout.setVisibility(View.VISIBLE);
            holder.itemMessageBinding.otherMessageLayout.setVisibility(View.GONE);
            holder.itemMessageBinding.myMessageText.setText(chatRoomModelArrayList.get(position).getChat_message());
            holder.itemMessageBinding.myMessageDate.setText(chatRoomModelArrayList.get(position).getChat_date());
        } else {
            holder.itemMessageBinding.myMessageLayout.setVisibility(View.GONE);
            holder.itemMessageBinding.otherMessageLayout.setVisibility(View.VISIBLE);
            holder.itemMessageBinding.otherMessageText.setText(chatRoomModelArrayList.get(position).getChat_message());
            holder.itemMessageBinding.otherMessageDate.setText(chatRoomModelArrayList.get(position).getChat_date());
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
