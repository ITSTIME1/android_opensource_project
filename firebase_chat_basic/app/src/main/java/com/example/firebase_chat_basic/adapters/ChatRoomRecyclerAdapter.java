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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase_chat_basic.databinding.ItemMessageBinding;
import com.example.firebase_chat_basic.model.ChatRoomModel;
import com.example.firebase_chat_basic.viewModel.ChatViewModel;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.Iterables;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class ChatRoomRecyclerAdapter extends RecyclerView.Adapter<ChatRoomRecyclerAdapter.CustomViewHolder> {
    private final SharedPreferences sharedPreferences;
    private final ArrayList<ChatRoomModel> chatRoomModelArrayList;
    private final Context context;

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

        int holderPosition = holder.getAdapterPosition();
        // 첫 값은 무조건 탑 데이트를 표시해주고
        if(holderPosition < 1) {
            // 첫번째 값이 0 인 값이 잘 나옴
            Log.d("holderposition", String.valueOf(holderPosition));
            holder.itemMessageBinding.myMessageTopDate.setVisibility(View.VISIBLE);
            holder.itemMessageBinding.myMessageTopDate.setText(chatRoomModelArrayList.get(holderPosition).getCurrent_date());
        } else {
            // 첫값이 아니라면 표시하지 않는다
            // 만약 댓글 중 이전 채팅의 날짜와 다르다면 표시해준다.
            // 리스트의 포지션 값의 currentDate 값이 그 전의 메세지 값의 currentDate 값을 비교 했을때 날짜가 다르다면 표시해준다.
            holder.itemMessageBinding.myMessageTopDate.setVisibility(View.GONE);
            if(!chatRoomModelArrayList.get(position).getCurrent_date().equals(chatRoomModelArrayList.get(position-1).getCurrent_date())) {
                final int lastIndex = chatRoomModelArrayList.size()-1;
                Log.d("lastIndex", String.valueOf(lastIndex));
                holder.itemMessageBinding.myMessageTopDate.setVisibility(View.VISIBLE);
                holder.itemMessageBinding.myMessageTopDate.setText(chatRoomModelArrayList.get(lastIndex).getCurrent_date());
            } else {
                Log.d("show?", "");
                // 만약 이전 날짜와 비교 했을 때 같다면 표시 하지 않는다.
                holder.itemMessageBinding.myMessageTopDate.setVisibility(View.GONE);
            }
        }




        // [ chat show logic ]
        if(get_key.equals(sharedPreferences.getString("authentication_uid", ""))) {
            // @TODO 마지막 채팅이 아니라면 layout을 다른걸로 보여준다.
            holder.itemMessageBinding.myMessageLayout.setVisibility(View.VISIBLE);
            holder.itemMessageBinding.otherMessageLayout.setVisibility(View.GONE);
            holder.itemMessageBinding.myMessageText.setText(chatRoomModelArrayList.get(position).getChat_message());
            // 마지막 값이라면 시간을 보여주고
            // 마지막 값이 아니라면 보여주지 않는다.
            if(!(chatRoomModelArrayList.get(position) ==  Iterables.getLast(chatRoomModelArrayList))) {
                holder.itemMessageBinding.myMessageDate.setVisibility(View.GONE);
            } else {
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
