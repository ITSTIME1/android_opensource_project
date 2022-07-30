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
        // 키 값이 나의 키 값이랑 같으면 my text, my date 에 적용

        // 1. 만약 처음 보낸 메세지라면(값을 가지고 왔는데 null 값이라면) 현재 데이트를 보여준다.
        // 2. 처음 보낸 메세지가 아니라면 전 채팅의 데이트 값이랑 비교해서 다르다면 보여준다.
        // 3. 만약 이전 데이트 값이랑 비교했을 때 이전 날짜에서 24시간이 지난 후의 시간이라면 +1 이라면 보여준다. = 그냥 날짜만 비교한다면 다르지 않을때 다 보여주게 된다

        // 바인드 되려면 데이터가 있어야 되는데 바인드가 되는 시기가 sendbutton을 눌렀을 때 바인드 되게 됨.
        if(!chatRoomModelArrayList.isEmpty()) {
            Date todayDateObject = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat todayDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            final String todayDate = todayDateFormat.format(todayDateObject);
            // 1. 채팅이 없어도 비어있는 상태로 오지를 않는다. 즉 채팅을 contact에서 처음 입장해서 sendbutton을 눌렀을 때 list에 추가 되게 됨. 첫 메세지가
            // 즉 처음 들어오는 메세지기 때문에 현재 날짜를 적용해줌.
            // 채팅이 비어있지 않다면
            // 전채팅 값의 데이트랑 최신 메세지 데이트 값이랑 비교해서 다음 날짜라면 표시해준다.
            // compareTo 메서드를 이용해서 현재 메세지 값이 이전 메세지 값보다
            // 이전 메세지의 데이트 값 보다 날짜가 이후라면 날짜를 보여준다(날짜가 다르기 때문)
            // 그리고 나서 setText 를 통해서 현재 recentChatDate를 입력해준다.안돼요", "");

            // 1. 이게 처음 가지고온 값
            // 1. 채팅의 개수가 2 미만 이라는건 채팅의 개수가 한개 밖에 없을때 == 채팅을 처음 시작했을 때 라는 뜻.
            if(chatRoomModelArrayList.size() < 2) {
                holder.itemMessageBinding.myMessageTopDate.setVisibility(View.VISIBLE);
                holder.itemMessageBinding.myMessageTopDate.setText(todayDate);
            } else {
                if(todayDate.compareTo(chatRoomModelArrayList.get(position).getCurrent_date()) > 0) {
                    holder.itemMessageBinding.myMessageTopDate.setVisibility(View.VISIBLE);
                    holder.itemMessageBinding.myMessageTopDate.setText(todayDate);
                    // 오늘날짜랑 같다면 보여주지 않는다.
                    // 그치만 여기서 채팅을 한번더 했을 때 데이트가 같기 때문에 이 조건문을 타고 처음에 표시한 날짜까지 다 지워버린다.
                } else if(todayDate.compareTo(chatRoomModelArrayList.get(position).getCurrent_date()) == 0){
                    // 처음에 표시한 값은 제외한다.
                    // 만약 날짜가 같아서 이 루프문을 탔다면
                    // 처음에 표시했던 값은 제외해야 되기 때문에
                    // 리스트에 있는 인덱스 값들을 전부 가지고 오면서 처음 인덱스랑 같은 값을 찾고
                    // 만약 같은 위치라면 ViewVisible 그렇지 않다면 gone
                    // 초기에 표시했던 값을 제외하고 나머지 날짜가 같은 값들에 대해서는뷰를 보여주지 않는다.
                    if(holder.getAdapterPosition() == 0) {
                        holder.itemMessageBinding.myMessageTopDate.setVisibility(View.VISIBLE);
                    }
                    holder.itemMessageBinding.myMessageTopDate.setVisibility(View.GONE);
                    Log.d("holder 개수", String.valueOf(holder.getAdapterPosition()));
                }
                // 채팅의 개수가 2개 이상이라면
                // 최근 보낸 채팅의 currentDate 값과 이전 인덱스의 currentDate 값을 비교해서
                // 만약 현재 데이트가 더 앞선다면 즉 더 날짜가 오래 되었다면 top을 표시해준다.
                // 만약 현재 데이트가 같다면 표시하지 않는다.

                // 오늘이 2022-07-30 이고 chatRoomModelArrayList.get(position).getCurrent_date() 값으로 가지고 온 날짜들이랑 비교를 해서
                // 오늘 날짜보다 날짜가 더 빠르다면 데이트를 표시하지 않고
                // 만약 오늘날짜보다 날짜가더 큰 currentDate 가 있다면 오늘 날짜를 표시한다.

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
