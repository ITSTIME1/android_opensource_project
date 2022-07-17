package com.example.firebase_chat_basic.adapters;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.firebase_chat_basic.databinding.ItemImageViewerBinding;
import com.example.firebase_chat_basic.model.ImageViewerModel;
import com.example.firebase_chat_basic.view.activity.PictureActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ImageViewerAdapter extends RecyclerView.Adapter<ImageViewerAdapter.ImageViewHolder> {
    private ArrayList<ImageViewerModel> imageViewerModelList;
    private Context context;


    public ImageViewerAdapter(ArrayList<ImageViewerModel> imageViewerModelList, Context context) {
        this.imageViewerModelList = imageViewerModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageViewerAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemImageViewerBinding itemImageViewerBinding = ItemImageViewerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ImageViewHolder(itemImageViewerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewerAdapter.ImageViewHolder holder, int position) {
        holder.binding(imageViewerModelList, position);
    }

    @Override
    public int getItemCount() {
        return imageViewerModelList.size();
    }

    public ImageViewerAdapter getImageAdapter(){
        return ImageViewerAdapter.this;
    }



    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ItemImageViewerBinding itemImageViewerBinding;

        public ImageViewHolder(@NonNull ItemImageViewerBinding itemImageViewerBinding) {
            super(itemImageViewerBinding.getRoot());
            this.itemImageViewerBinding = itemImageViewerBinding;
        }

        public void binding(ArrayList<ImageViewerModel> imageViewerModelList, int pos){
            itemImageViewerBinding.pictureImage.setImageURI(Uri.parse(imageViewerModelList.get(pos).getImage_viewer()));
            itemImageViewerBinding.executePendingBindings();
        }

    }
}
