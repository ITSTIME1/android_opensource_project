package com.example.firebase_chat_basic.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase_chat_basic.custom.LinearLayoutCustomClass;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;

/**
 * [BindCustomAdapter]
 *
 * <Topic>
 *
 *     This activity is "custom adapter space".
 *     that's mean that we need a "custom linearlayout or custom recyclerview".
 *     I think that it's very important because I used to data-binding but if i write a lot of glue code, it will perform poorly.
 *
 * </Topic>
 */


public class BindCustomAdapter {


    @BindingAdapter("ChatAdapter")
    public static void chatUserListAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.smoothScrollToPosition(adapter.getItemCount());
    }
    @BindingAdapter("ContactAdapter")
    public static void contactUserListAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("RoomAdapter")
    public static void chatRoomMessageAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutCustomClass(recyclerView.getContext(), LinearLayoutCustomClass.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("ImageViewer")
    public static void imageViewerAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
