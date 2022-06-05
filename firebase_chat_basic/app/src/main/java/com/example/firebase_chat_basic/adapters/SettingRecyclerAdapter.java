package com.example.firebase_chat_basic.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SettingRecyclerAdapter extends RecyclerView.Adapter<SettingRecyclerAdapter.CustomSettingViewHolder> {
    @NonNull
    @Override
    public SettingRecyclerAdapter.CustomSettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SettingRecyclerAdapter.CustomSettingViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CustomSettingViewHolder extends RecyclerView.ViewHolder {
        public CustomSettingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
