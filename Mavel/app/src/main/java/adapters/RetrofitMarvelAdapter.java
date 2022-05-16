package adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mavel.DetailActivity;
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


            /*
             * [itemView Click Listener]
             */
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION) {

                        String detail_Image = resultList.get(pos).getPosterPath();
                        String detail_title = resultList.get(pos).getOriginalTitle();
                        String detail_releaseTime = resultList.get(pos).getReleaseDate();
                        Double detail_average = resultList.get(pos).getVoteAverage();
                        String detail_overView = resultList.get(pos).getOverview();
                        Double detail_popularity = resultList.get(pos).getPopularity();

                        /*
                         * [Intent]
                         */
                        Intent detailIntent = new Intent(view.getContext(), DetailActivity.class);

                        detailIntent.putExtra("detail_Image", detail_Image);
                        detailIntent.putExtra("detail_title", detail_title);
                        detailIntent.putExtra("detail_releaseTime", detail_releaseTime);
                        detailIntent.putExtra("detail_average", detail_average);
                        detailIntent.putExtra("detail_overView", detail_overView);
                        detailIntent.putExtra("detail_popularity", detail_popularity);

                        view.getContext().startActivity(detailIntent);

                    }
                }
            });

        }
    }
}
