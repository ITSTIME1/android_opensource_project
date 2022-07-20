package com.example.firebase_chat_basic.adapters;

import android.content.Context;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase_chat_basic.custom.LinearLayoutCustomClass;

/**
 * [BindCustomAdapter]
 *
 * This is very usefully because that using with databinding automatically setAdapter.
 * and then using "ViewModel" more usefully because that in xml can setAdapter.
 */


public class BindCustomAdapter {

    @BindingAdapter("ChatAdapter")
    public static void chatAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.smoothScrollToPosition(adapter.getItemCount());
    }
    @BindingAdapter("ContactAdapter")
    public static void contactAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("RoomAdapter")
    public static void chatRoomAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
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

    @BindingAdapter("VideoViewer")
    public static void videoViewerAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

}
