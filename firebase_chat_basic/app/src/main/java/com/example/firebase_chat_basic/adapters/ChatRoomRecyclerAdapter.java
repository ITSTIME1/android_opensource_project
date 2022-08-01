package com.example.firebase_chat_basic.adapters;

;
import android.annotation.SuppressLint;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        Date todayDateObject = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat todayDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String todayDate = todayDateFormat.format(todayDateObject);

        // 채팅 데이트 표시하기
        // 1. 첫번째 채팅 했을 시 표시
        // 2. 첫번째 값이 아닐 경우
        // 3. 오늘 날짜랑 같다면 표시하지 않고
        // 4. 오늘날짜보다 이후의 값이라면 표시

        if(chatRoomModelArrayList.get(position).equals(chatRoomModelArrayList.get(0))) {
            holder.itemMessageBinding.myMessageTopDate.setVisibility(View.VISIBLE);
            holder.itemMessageBinding.myMessageTopDate.setText(chatRoomModelArrayList.get(0).getCurrent_date());
        } else {
            if(chatRoomModelArrayList.get(position).getCurrent_date().equals(todayDate)) {
                // 첫번째 값이 아닌 위치부터 검사해서 오늘 날짜랑 같다면 표시하지 않는다.
                holder.itemMessageBinding.myMessageTopDate.setVisibility(View.GONE);
            } else {
                // 그럼 만약에 첫번째 값을 제외하고 다른 채팅의 데이트를 검사했는데 오늘 날짜랑 다르다면
                holder.itemMessageBinding.myMessageTopDate.setVisibility(View.VISIBLE);
                holder.itemMessageBinding.myMessageTopDate.setText(chatRoomModelArrayList.get(position).getCurrent_date());
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
