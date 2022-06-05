package com.example.firebase_chat_basic.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactRecyclerAdapter.CustomContactViewHolder> {
    @NonNull
    @Override
    public ContactRecyclerAdapter.CustomContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactRecyclerAdapter.CustomContactViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CustomContactViewHolder extends RecyclerView.ViewHolder {
        public CustomContactViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
