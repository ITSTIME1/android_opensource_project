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

        /**
         * [ TopDate Show Function ]
         *
         * <Topic>
         *     1. 처음 채팅 한다면 top_head에 날짜를 보여준다.
         *     2. 첫 채팅을 제외하고 다른 채팅 값의 current_date 값이 오늘 날짜와 같을 경우 top_head에 표시하지 않는다.
         *     3. 만약 메세지를 보냈는데 최근에 보냈던 메세지 의 currentDate 값보다 더 이후의 값이라면 top_head에 표시하지 않는다.
         *     4. 이후에도 currentDate 값이 계속 다르게 들어오면 계속적으로 표시 되기 때문에 한번만 표시해준다.
         * </Topic>
         */
        if(!chatRoomModelArrayList.isEmpty()) {
            Date todayDateObject = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat todayDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            final String todayDate = todayDateFormat.format(todayDateObject);
            Log.d("todayDate ", todayDate);

            // 1. 처음 채팅을 했다면
            if(holder.getAdapterPosition() == 0 && chatRoomModelArrayList.size() < 2) {
                // 데이터를 표시해준다.
                holder.itemMessageBinding.myMessageTopDate.setVisibility(View.VISIBLE);
                // 처음 데이터의 current_date 값을 넣어준다.
                holder.itemMessageBinding.myMessageTopDate.setText(chatRoomModelArrayList.get(0).getCurrent_date());
            } else {
                int adapterPosition = holder.getAdapterPosition();
                // for문을 돌려서 첫번째 값이라면 계속 보여주어야 하니까 break 걸고
                // 채팅이 2개 이상이라는 것이기 때문에
                // 1. 만약 첫 번째 채팅을 제외하고 나머지 채팅의 current_date 값을 가지고 왔을 때 현재 날짜랑 같다면 표시하지 않고 값도 넣지 않는다.
                // 2. 그리고 오늘날짜랑 비교해서 가지고온 current_date 갑소가 비교했을 때 오늘 날짜가 더 크다면 2022-07-31 > 2022-07-30
                // 3. 하지만 그럼 가지고 오는 데이터가 현재 데이터 보다 앞

                // @TODO 오늘 밤 12시가 지나고 표시 되는지 확인해야됨.
                if(chatRoomModelArrayList.get(position).getCurrent_date().equals(todayDate)) {
                    // 첫번째 값은 보여준다.
                    if(chatRoomModelArrayList.get(position) == chatRoomModelArrayList.get(0)) {
                        holder.itemMessageBinding.myMessageTopDate.setVisibility(View.VISIBLE);
                        holder.itemMessageBinding.myMessageTopDate.setText(chatRoomModelArrayList.get(position).getCurrent_date());
                    } else {
                        // 첫번째 값이 아닌 것들
                        // 중에서 그 position 의 날짜가 오늘 날짜와 같다면 보여주지 않는다.
                        if(chatRoomModelArrayList.get(position).getCurrent_date().equals(todayDate)) {
                            holder.itemMessageBinding.myMessageTopDate.setVisibility(View.GONE);
                        }
                    }
                } else {
                    holder.itemMessageBinding.myMessageTopDate.setVisibility(View.VISIBLE);
                    holder.itemMessageBinding.myMessageDate.setText(todayDate);
                }
            }


        } else {
            Log.d("리스트가 비어있습니다.", "");
;       }

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
