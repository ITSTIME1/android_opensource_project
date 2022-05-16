package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mavel.R;

import java.util.List;

import model.Result;

/**
 * [RetrofitMarvelAvengersAdapter]
 *
 */

public class RetrofitMarvelAvengersAdapter extends RecyclerView.Adapter<RetrofitMarvelAvengersAdapter.ViewHolder> {

    List<Result> resultList2;
    Context context;

    public RetrofitMarvelAvengersAdapter(List<Result> resultList2, Context context) {
        this.resultList2 = resultList2;
        this.context = context;
    }

    @NonNull
    @Override
    public RetrofitMarvelAvengersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.marvel_avengers_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RetrofitMarvelAvengersAdapter.ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500/"+resultList2.get(position).getPosterPath())
                .into(holder.avengersImageView);

    }

    @Override
    public int getItemCount() {
        return resultList2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avengersImageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avengersImageView = itemView.findViewById(R.id.avengers_image);
        }
    }
}
