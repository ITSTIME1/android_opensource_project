package com.example.firebase_chat_basic.model;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;

/**
 * [ChatRoomModel]
 *
 * <Topic>
 *
 *     This model is for "chat room every message"
 *
 * </Topic>
 *
 */
public class ChatRoomModel {
    private String setKey;
    private String chat_message;
    private String chat_date;
    private String current_date;
    private int viewType;
    private String url;
    private ExoPlayer exoPlayer;



    // overloading image constructor
    public ChatRoomModel(String setKey, String chat_date, String current_date, int viewType, String url) {
        this.setKey = setKey;
        this.chat_date = chat_date;
        this.current_date = current_date;
        this.viewType = viewType;
        this.url = url;
    }

    // overloading message constructor
    public ChatRoomModel(String setKey, String chat_message, String chat_date, String current_date, int viewType) {
        this.setKey = setKey;
        this.chat_message = chat_message;
        this.chat_date = chat_date;
        this.current_date = current_date;
        this.viewType = viewType;
    }

    // overloading video constructor
    public ChatRoomModel(String setKey, String chat_date, String current_date, int viewType, ExoPlayer exoPlayer) {
        this.setKey = setKey;
        this.chat_date = chat_date;
        this.current_date = current_date;
        this.viewType = viewType;
        this.exoPlayer = exoPlayer;
    }

    public ExoPlayer getExoPlayer() {
        return exoPlayer;
    }

    public void setExoPlayer(ExoPlayer exoPlayer) {
        this.exoPlayer = exoPlayer;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }

    public String getSetKey() {
        return setKey;
    }

    public void setSetKey(String setKey) {
        this.setKey = setKey;
    }

    public String getChat_message() {
        return chat_message;
    }

    public void setChat_message(String chat_message) {
        this.chat_message = chat_message;
    }

    public String getChat_date() {
        return chat_date;
    }

    public void setChat_date(String chat_date) {
        this.chat_date = chat_date;
    }
}

