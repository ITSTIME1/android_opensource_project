package com.example.firebase_chat_basic.adapters;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.constants.Constants;
import com.example.firebase_chat_basic.databinding.ItemImageViewerBinding;
import com.example.firebase_chat_basic.databinding.ItemMessageBinding;
import com.example.firebase_chat_basic.model.ChatRoomModel;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.Iterables;
import java.util.ArrayList;




// @TODO 날짜 표시해주는 로직 다시 생각해보자.
public class ChatRoomRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final SharedPreferences sharedPreferences;
    private final ArrayList<ChatRoomModel> chatRoomModelArrayList;


    public ChatRoomRecyclerAdapter(ArrayList<ChatRoomModel> chatRoomModelArrayList, Context context) {
        this.chatRoomModelArrayList = chatRoomModelArrayList;
        sharedPreferences = context.getSharedPreferences("authentication", Activity.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMessageBinding itemMessageBinding = ItemMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ItemImageViewerBinding itemImageViewerBinding = ItemImageViewerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        if(viewType == Constants.chatMessageViewType) {
            return new ChatMessageViewHolder(itemMessageBinding);
        } else {
            return new ChatImageViewHolder(itemImageViewerBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        // chat message data binding
        // 채팅 메세지를 보여주는것
        if(holder instanceof ChatMessageViewHolder) {
            Log.d("chatArrayList 개수 좀보자 ", String.valueOf(chatRoomModelArrayList.get(position)));
            int holderPosition = holder.getAdapterPosition();
            int maxMessage = chatRoomModelArrayList.size() - 1;
            Log.d("maxMessage 확인좀 해보자", String.valueOf(maxMessage));
            if(holderPosition == 0) {
                ((ChatMessageViewHolder) holder).itemMessageBinding.myMessageTopDate.setVisibility(View.VISIBLE);
                ((ChatMessageViewHolder) holder).itemMessageBinding.myMessageTopDate.setText(chatRoomModelArrayList.get(holderPosition).getCurrent_date());
            } else {
                ((ChatMessageViewHolder) holder).itemMessageBinding.myMessageTopDate.setVisibility(View.GONE);
            }

            Animation myChatAnimation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.chat_room_message_my_anim);
            Animation otherChatAnimation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.chat_room_message_other_anim);

            final String get_key = chatRoomModelArrayList.get(position).getSetKey();
            if(get_key.equals(sharedPreferences.getString("authentication_uid", ""))) {
                ((ChatMessageViewHolder) holder).itemMessageBinding.myMessageLayout.setVisibility(View.VISIBLE);
                ((ChatMessageViewHolder) holder).itemMessageBinding.otherMessageLayout.setVisibility(View.GONE);
                ((ChatMessageViewHolder) holder).itemMessageBinding.myMessageText.setText(chatRoomModelArrayList.get(position).getChat_message());
                // 마지막 값이라면 시간을 보여주고
                // 마지막 값이 아니라면 보여주지 않는다.
                if(!(chatRoomModelArrayList.get(position) == Iterables.getLast(chatRoomModelArrayList))) {
                    ((ChatMessageViewHolder) holder).itemMessageBinding.myMessageDate.setVisibility(View.GONE);
                } else {
                    // animation 적용
                    ((ChatMessageViewHolder) holder).itemMessageBinding.myMessageLayout.setAnimation(myChatAnimation);
                    ((ChatMessageViewHolder) holder).itemMessageBinding.myMessageDate.setVisibility(View.VISIBLE);
                    ((ChatMessageViewHolder) holder).itemMessageBinding.myMessageDate.setText(chatRoomModelArrayList.get(position).getChat_date());
                }
            } else {
                ((ChatMessageViewHolder) holder).itemMessageBinding.myMessageLayout.setVisibility(View.GONE);
                ((ChatMessageViewHolder) holder).itemMessageBinding.otherMessageLayout.setVisibility(View.VISIBLE);
                ((ChatMessageViewHolder) holder).itemMessageBinding.otherMessageText.setText(chatRoomModelArrayList.get(position).getChat_message());
                if(!(chatRoomModelArrayList.get(position) ==  Iterables.getLast(chatRoomModelArrayList))) {
                    ((ChatMessageViewHolder) holder).itemMessageBinding.otherMessageDate.setVisibility(View.GONE);
                } else {
                    ((ChatMessageViewHolder) holder).itemMessageBinding.myMessageLayout.setAnimation(otherChatAnimation);
                    ((ChatMessageViewHolder) holder).itemMessageBinding.otherMessageDate.setVisibility(View.VISIBLE);
                    ((ChatMessageViewHolder) holder).itemMessageBinding.otherMessageDate.setText(chatRoomModelArrayList.get(position).getChat_date());
                }
            }
        } else if (holder instanceof ChatImageViewHolder) {
            // @TODO Write imageViewHolder code.
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

    @Override
    public int getItemViewType(int position) {
        return chatRoomModelArrayList.get(position).getViewType();
    }

    // chat view holder
    public class ChatMessageViewHolder extends RecyclerView.ViewHolder {
        ItemMessageBinding itemMessageBinding;

        public ChatMessageViewHolder(@NonNull ItemMessageBinding itemMessageBinding) {
            super(itemMessageBinding.getRoot());
            this.itemMessageBinding = itemMessageBinding;
        }
    }

    // image view holder
    public class ChatImageViewHolder extends RecyclerView.ViewHolder {
        ItemImageViewerBinding itemImageViewerBinding;
        public ChatImageViewHolder(@NonNull ItemImageViewerBinding itemImageViewerBinding) {
            super(itemImageViewerBinding.getRoot());
            this.itemImageViewerBinding = itemImageViewerBinding;
        }
    }

}
