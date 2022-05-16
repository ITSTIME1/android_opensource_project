package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mavel.R;

import java.util.List;

import model.Result;

/**
 * [RetrofitMarvelAdapter]
 *
 * In this page, I used to "Glide" for imageLoad.
 *
 */

public class RetrofitMarvelAdapter extends RecyclerView.Adapter<RetrofitMarvelAdapter.ViewHolder> {

    List<Result> resultList;
    Context context;

    public RetrofitMarvelAdapter(List<Result> resultList, Context context) {
        this.resultList = resultList;
        this.context = context;
    }

    @NonNull
    @Override
    public RetrofitMarvelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mavel_image_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RetrofitMarvelAdapter.ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500/"+resultList.get(position).getPosterPath())
                .into(holder.marVelImageView);

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView marVelImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            marVelImageView = itemView.findViewById(R.id.marvel_image);
        }
    }
}
