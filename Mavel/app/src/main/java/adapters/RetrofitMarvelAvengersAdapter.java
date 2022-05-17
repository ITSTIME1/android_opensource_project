package adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.example.mavel.DetailActivity;
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


            /*
             * [itemView Click Listener]
             */
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION) {

                        String detail_Image = resultList2.get(pos).getPosterPath();
                        String detail_title = resultList2.get(pos).getOriginalTitle();
                        String detail_releaseTime = resultList2.get(pos).getReleaseDate();
                        Double detail_average = resultList2.get(pos).getVoteAverage();
                        String detail_overView = resultList2.get(pos).getOverview();
                        Double detail_popularity = resultList2.get(pos).getPopularity();

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

                        /**
                         * [Animator Transition]
                         */
                        Animatoo.animateZoom(view.getContext());
                    }
                }
            });
        }
    }
}
