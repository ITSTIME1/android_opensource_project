package com.example.firebase_chat_basic.adapters;
import static com.google.android.exoplayer2.Player.STATE_IDLE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.constants.Constants;
import com.example.firebase_chat_basic.databinding.ItemMessageBinding;
import com.example.firebase_chat_basic.databinding.ItemMessageImageBinding;
import com.example.firebase_chat_basic.databinding.ItemVideoBinding;
import com.example.firebase_chat_basic.model.ChatRoomModel;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Tracks;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.Iterables;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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
public class ChatRoomRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final SharedPreferences sharedPreferences;
    private final ArrayList<ChatRoomModel> chatRoomModelArrayList;
    private Context context;

    public ChatRoomRecyclerAdapter(ArrayList<ChatRoomModel> chatRoomModelArrayList, Context context) {
        this.chatRoomModelArrayList = chatRoomModelArrayList;
        sharedPreferences = context.getSharedPreferences("authentication", Activity.MODE_PRIVATE);
        this.context = context;
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
            return new VideoViewHolder(itemVideoBinding);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final String get_key = chatRoomModelArrayList.get(position).getSetKey();
        // chat message viewHolder
        if (holder instanceof ChatMessageViewHolder) {
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
            // gif, image viewHolder
        } else if (holder instanceof ChatImageViewHolder) {
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
            // @TODO 다시 한번 짜야됨.
        } else if (holder instanceof VideoViewHolder) {
            if (get_key.equals(sharedPreferences.getString("authentication_uid", ""))) {
                // state 상태를 보자

                Log.d("videoURL확인", chatRoomModelArrayList.get(position).getUrl());
                if(chatRoomModelArrayList.get(position).getExoPlayer() != null) {
                    ProgressiveMediaSource mediaSource = new ProgressiveMediaSource
                            .Factory(chatRoomModelArrayList.get(position).getFactory())
                            .createMediaSource(MediaItem.fromUri(chatRoomModelArrayList.get(position).getUrl()));
                    chatRoomModelArrayList.get(position).getExoPlayer().setMediaSource(mediaSource);
                    ((VideoViewHolder) holder).itemVideoBinding.styledPlayerView.setPlayer(chatRoomModelArrayList.get(position).getExoPlayer());
                    chatRoomModelArrayList.get(position).getExoPlayer().prepare();
                    chatRoomModelArrayList.get(position).getExoPlayer().setPlayWhenReady(true);
                }

            }
        }
    }

    // exoPlayer detach
//    @Override
//    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
//        if (holder instanceof VideoViewHolder) {
//            int position = holder.getAbsoluteAdapterPosition();
//            // 널이라면
//            if (chatRoomModelArrayList.get(position) == null) {
//                chatRoomModelArrayList.get(position).getExoPlayer().release();
//            }
//        }
//        super.onViewDetachedFromWindow(holder);
//    }




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

        // chat view holder
        public static class ChatMessageViewHolder extends RecyclerView.ViewHolder {
            ItemMessageBinding itemMessageBinding;

            public ChatMessageViewHolder(@NonNull ItemMessageBinding itemMessageBinding) {
                super(itemMessageBinding.getRoot());
                this.itemMessageBinding = itemMessageBinding;
            }
        }

        // image view holder
        public class ChatImageViewHolder extends RecyclerView.ViewHolder {
            ItemMessageImageBinding itemImageViewerBinding;

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
                        LayoutInflater factory = LayoutInflater.from(view.getContext());
                        @SuppressLint("InflateParams") final View alertDialogView = factory.inflate(R.layout.activity_chat_room_image_alert_dialog_view, null);
                        String alertDialogImageURL = chatRoomModelArrayList.get(pos).getUrl();
                        ImageView alertDialogImageViewId = alertDialogView.findViewById(R.id.chat_room_image_alert_dialog_view);
                        Glide.with(view.getContext()).load(alertDialogImageURL).into(alertDialogImageViewId);
                        alertDialog.setView(alertDialogView);
                        alertDialog.show();
                    }
                });
            }
        }

        // video viewholder
        public static class VideoViewHolder extends RecyclerView.ViewHolder {
            ItemVideoBinding itemVideoBinding;

            public VideoViewHolder(ItemVideoBinding itemVideoBinding) {
                super(itemVideoBinding.getRoot());
                this.itemVideoBinding = itemVideoBinding;
            }
        }
}
