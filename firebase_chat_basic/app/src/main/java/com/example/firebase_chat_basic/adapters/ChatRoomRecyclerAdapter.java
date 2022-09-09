package com.example.firebase_chat_basic.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.constants.Constants;
import com.example.firebase_chat_basic.databinding.FragmentCustomVideoBinding;
import com.example.firebase_chat_basic.databinding.ItemMessageBinding;
import com.example.firebase_chat_basic.databinding.ItemMessageImageBinding;
import com.example.firebase_chat_basic.databinding.ItemVideoBinding;
import com.example.firebase_chat_basic.model.ChatRoomModel;
import com.example.firebase_chat_basic.view.fragment.CustomVideoDialogFragment;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.Iterables;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

/**
 * [ChatRoomRecyclerAdapter]
 *
 * <Topic>
 *
 *     This adapter is for "chatRoomMessage"
 *     you have to know that this adapter has two viewType and two viewHolder class.
 *
 *     First. "ChatMessageViewHolder"
 *     Second. "ChatImageViewHolder"
 *
 *     if you get data from "ChatViewModel", this adapter will separate the data.
 *     if data is "msg", set into "ChatMessageViewHolder"
 *     if data is "imageURI", set into "ChatImageViewHolder"
 *     
 * </Topic>
 */
// @TODO 날짜 표시해주는 로직 다시 생각해보자.
public class ChatRoomRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final SharedPreferences sharedPreferences;
    private final ArrayList<ChatRoomModel> chatRoomModelArrayList;
    private final FragmentManager fragmentManager;
    private final ExoPlayer exoPlayer;
    private final DataSource.Factory factory;
    private LayoutInflater layoutInflater;

    public ChatRoomRecyclerAdapter(ArrayList<ChatRoomModel> chatRoomModelArrayList, Context context, FragmentManager fragmentManager, ExoPlayer exoPlayer, DataSource.Factory factory) {
        this.chatRoomModelArrayList = chatRoomModelArrayList;
        sharedPreferences = context.getSharedPreferences("authentication", Activity.MODE_PRIVATE);
        this.fragmentManager = fragmentManager;
        this.exoPlayer = exoPlayer;
        this.factory = factory;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMessageBinding itemMessageBinding = ItemMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ItemMessageImageBinding itemMessageImageBinding = ItemMessageImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ItemVideoBinding itemVideoBinding = ItemVideoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        if(viewType == Constants.chatMessageViewType) {
            return new ChatMessageViewHolder(itemMessageBinding);
        } else if (viewType == Constants.chatImageViewType) {
            return new ChatImageViewHolder(itemMessageImageBinding);
        } else {
            return new ChatVideoViewHolder(itemVideoBinding);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final String get_key = chatRoomModelArrayList.get(position).getSetKey();
        if (holder instanceof ChatMessageViewHolder) {
            // chat message bind
            chatMessageBind((ChatMessageViewHolder) holder, get_key, position);

        } else if (holder instanceof ChatImageViewHolder) {

            // chat image bind
            chatImageBind((ChatImageViewHolder) holder, get_key, position);

        } else if (holder instanceof ChatVideoViewHolder) {

            // chat video bind
            chatVideoBind((ChatVideoViewHolder) holder, get_key, position);
        }
    }



    // chatMessageViewHolder bind data
    public void chatMessageBind(ChatMessageViewHolder holder, String get_key, int position){
        // first message
        int holderPosition = holder.getAbsoluteAdapterPosition();

        if (holderPosition == 0) {
            ((ChatMessageViewHolder) holder).itemMessageBinding.myMessageTopDate.setVisibility(View.VISIBLE);
            ((ChatMessageViewHolder) holder).itemMessageBinding.myMessageTopDate.setText(chatRoomModelArrayList.get(holderPosition).getCurrent_date());
        } else {
            ((ChatMessageViewHolder) holder).itemMessageBinding.myMessageTopDate.setVisibility(View.GONE);
        }

        Animation myChatAnimation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.chat_room_message_my_anim);
        Animation otherChatAnimation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.chat_room_message_other_anim);


        if (get_key.equals(sharedPreferences.getString("authentication_uid", ""))) {
            ((ChatMessageViewHolder) holder).itemMessageBinding.myMessageLayout.setVisibility(View.VISIBLE);
            ((ChatMessageViewHolder) holder).itemMessageBinding.otherMessageLayout.setVisibility(View.GONE);
            // image, message
            // setText 다르게
            ((ChatMessageViewHolder) holder).itemMessageBinding.myMessageText.setText(chatRoomModelArrayList.get(position).getChat_message());

            // 마지막 값이라면 시간을 보여주고
            // 마지막 값이 아니라면 보여주지 않는다.
            if (!(chatRoomModelArrayList.get(position) == Iterables.getLast(chatRoomModelArrayList))) {
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
            if (!(chatRoomModelArrayList.get(position) == Iterables.getLast(chatRoomModelArrayList))) {
                ((ChatMessageViewHolder) holder).itemMessageBinding.otherMessageDate.setVisibility(View.GONE);
            } else {
                ((ChatMessageViewHolder) holder).itemMessageBinding.myMessageLayout.setAnimation(otherChatAnimation);
                ((ChatMessageViewHolder) holder).itemMessageBinding.otherMessageDate.setVisibility(View.VISIBLE);
                ((ChatMessageViewHolder) holder).itemMessageBinding.otherMessageDate.setText(chatRoomModelArrayList.get(position).getChat_date());
            }
        }
    }

    // chatImageViewHolder bind data
    public void chatImageBind(ChatImageViewHolder holder, String get_key, int position){
        if (get_key.equals(sharedPreferences.getString("authentication_uid", ""))) {
            Glide.with(holder.itemView.getContext())
                    .load(chatRoomModelArrayList
                            .get(position).getUrl())
                    .into(((ChatImageViewHolder) holder)
                            .itemImageViewerBinding.myMessageImageView);
            ((ChatImageViewHolder) holder).itemImageViewerBinding.myMessageImageView.setVisibility(View.VISIBLE);
            ((ChatImageViewHolder) holder).itemImageViewerBinding.otherMessageImageView.setVisibility(View.GONE);
            ((ChatImageViewHolder) holder).itemImageViewerBinding.myMessageImageDate.setVisibility(View.VISIBLE);
            ((ChatImageViewHolder) holder).itemImageViewerBinding.otherMessageImageDate.setVisibility(View.GONE);
            ((ChatImageViewHolder) holder).itemImageViewerBinding.myMessageImageDate.setText(chatRoomModelArrayList.get(position).getCurrent_date());

        } else {
            Glide.with(holder.itemView.getContext())
                    .load(chatRoomModelArrayList
                            .get(position).getUrl())
                    .into(((ChatImageViewHolder) holder)
                            .itemImageViewerBinding.otherMessageImageView);
            ((ChatImageViewHolder) holder).itemImageViewerBinding.otherMessageImageView.setVisibility(View.GONE);
            ((ChatImageViewHolder) holder).itemImageViewerBinding.myMessageImageLayout.setVisibility(View.VISIBLE);
            ((ChatImageViewHolder) holder).itemImageViewerBinding.myMessageImageDate.setVisibility(View.GONE);
            ((ChatImageViewHolder) holder).itemImageViewerBinding.otherMessageImageDate.setVisibility(View.VISIBLE);
            ((ChatImageViewHolder) holder).itemImageViewerBinding.myMessageImageDate.setText(chatRoomModelArrayList.get(position).getCurrent_date());
        }
    }

    // chatVideoViewHolder bind data
    public void chatVideoBind(ChatVideoViewHolder holder, String get_key, int position){
        if (get_key.equals(sharedPreferences.getString("authentication_uid", ""))) {
            ((ChatVideoViewHolder) holder).myInit(chatRoomModelArrayList, position);
        } else {
            ((ChatVideoViewHolder) holder).otherInit();
        }
    }



    @Override
    public int getItemCount () {
        if (chatRoomModelArrayList.size() == 0) {
            return 0;
        } else {
            return chatRoomModelArrayList.size();
        }
    }
    @Override
    public int getItemViewType (int position){
        return chatRoomModelArrayList.get(position).getViewType();
    }
    @Override
    public long getItemId(int position) {
        return chatRoomModelArrayList.get(position).hashCode();
    }



    // chat view holder
        public static class ChatMessageViewHolder extends RecyclerView.ViewHolder {
            public ItemMessageBinding itemMessageBinding;

            public ChatMessageViewHolder(@NonNull ItemMessageBinding itemMessageBinding) {
                super(itemMessageBinding.getRoot());
                this.itemMessageBinding = itemMessageBinding;
            }
        }

        // image view holder
        public class ChatImageViewHolder extends RecyclerView.ViewHolder {
            public ItemMessageImageBinding itemImageViewerBinding;

            public ChatImageViewHolder(@NonNull ItemMessageImageBinding itemImageViewerBinding) {
                super(itemImageViewerBinding.getRoot());
                this.itemImageViewerBinding = itemImageViewerBinding;

                itemImageViewerBinding.myMessageImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = getAbsoluteAdapterPosition();
                        Log.d("pos", String.valueOf(pos));
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        alertDialog.setCanceledOnTouchOutside(true);
                        layoutInflater = LayoutInflater.from(view.getContext());
                        @SuppressLint("InflateParams") final View alertDialogView = layoutInflater.inflate(R.layout.activity_chat_room_image_alert_dialog_view, null);
                        String alertDialogImageURL = String.valueOf(chatRoomModelArrayList.get(pos).getUrl());
                        ImageView alertDialogImageViewId = alertDialogView.findViewById(R.id.chat_room_image_alert_dialog_view);
                        Glide.with(view.getContext()).load(alertDialogImageURL).into(alertDialogImageViewId);
                        alertDialog.setView(alertDialogView);
                        alertDialog.show();
                    }
                });
            }
        }

        // video ViewHolder
        public class ChatVideoViewHolder extends RecyclerView.ViewHolder {
            public ItemVideoBinding itemVideoBinding;
            public CustomVideoDialogFragment customVideoDialogFragment;
            public ChatVideoViewHolder(ItemVideoBinding itemVideoBinding) {
                super(itemVideoBinding.getRoot());
                this.itemVideoBinding = itemVideoBinding;
            }
            // initialize my
            public void myInit(ArrayList<ChatRoomModel> chatRoomModelArrayList, int position){

                Glide.with(itemVideoBinding.getRoot())
                        .load(chatRoomModelArrayList.get(position).getUrl())
                        .into(itemVideoBinding.customVideoImageView);

                itemVideoBinding.customVideoImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = getAbsoluteAdapterPosition();
                        customVideoDialogFragment = new CustomVideoDialogFragment(chatRoomModelArrayList.get(pos).getUrl(), exoPlayer, factory);
                        customVideoDialogFragment.show(fragmentManager, "video");
                    }
                });
            }
            // initialize other
            // @TODO 작업해야됨.
            public void otherInit(){}
        }

}
