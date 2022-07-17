package com.example.firebase_chat_basic.adapters;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.firebase_chat_basic.databinding.ItemImageViewerBinding;
import com.example.firebase_chat_basic.model.ImageViewerModel;
import java.util.ArrayList;



public class ImageViewerAdapter extends RecyclerView.Adapter<ImageViewerAdapter.ImageViewHolder> {
    private final ArrayList<ImageViewerModel> imageViewerModelList;

    public ImageViewerAdapter(ArrayList<ImageViewerModel> imageViewerModelList) {
        this.imageViewerModelList = imageViewerModelList;
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


    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ItemImageViewerBinding itemImageViewerBinding;

        public ImageViewHolder(@NonNull ItemImageViewerBinding itemImageViewerBinding) {
            super(itemImageViewerBinding.getRoot());
            this.itemImageViewerBinding = itemImageViewerBinding;
        }

        public void binding(ArrayList<ImageViewerModel> imageViewerModelList, int pos){
            Glide.with(itemImageViewerBinding.pictureImage.getContext())
                    .load(imageViewerModelList.get(pos).getImage_viewer()).into(itemImageViewerBinding.pictureImage);
            itemImageViewerBinding.executePendingBindings();
        }

    }
}
