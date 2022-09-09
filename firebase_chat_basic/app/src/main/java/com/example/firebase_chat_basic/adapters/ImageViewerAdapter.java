package com.example.firebase_chat_basic.adapters;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.firebase_chat_basic.databinding.ItemImageViewerBinding;
import com.example.firebase_chat_basic.model.ImageViewerModel;
import java.util.ArrayList;


/**
 * [ImageViewer Adapter]
 *
 * <Topic>
 *
 *     This adapter displays that you selected image.
 *     displays according to the number of images.
 *
 * </Topic>
 */

public class ImageViewerAdapter extends RecyclerView.Adapter<ImageViewerAdapter.ImageViewHolder> {
    private final ArrayList<ImageViewerModel> imageViewerModelList;
    private final Context context;

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
        holder.init(imageViewerModelList, position);
    }

    @Override
    public int getItemCount() {
        return imageViewerModelList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ItemImageViewerBinding itemImageViewerBinding;

        public ImageViewHolder(@NonNull ItemImageViewerBinding itemImageViewerBinding) {
            super(itemImageViewerBinding.getRoot());
            this.itemImageViewerBinding = itemImageViewerBinding;
        }

        public void init(ArrayList<ImageViewerModel> imageViewerModelList, int pos){
            Glide.with(context).load(imageViewerModelList.get(pos).getImage_viewer()).into(itemImageViewerBinding.pictureImage);
            itemImageViewerBinding.executePendingBindings();
        }

    }
}
