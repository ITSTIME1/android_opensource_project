package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;

import java.util.ArrayList;
import java.util.List;

import Models.HomeRecommendModel;

public class HomeHolRecoAdapter extends RecyclerView.Adapter<HomeHolRecoAdapter.ViewHolder> {

    private ArrayList<HomeRecommendModel> homeRecommendModelList;
    private Context context;

    public HomeHolRecoAdapter(ArrayList<HomeRecommendModel> homeRecommendModelList, Context context) {
        this.homeRecommendModelList = homeRecommendModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeHolRecoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_home_recommend_hol_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHolRecoAdapter.ViewHolder holder, int position) {
        holder.homeHolRecommendImageView.setImageResource(homeRecommendModelList.get(position).getHomeHolRecommendImageView());
    }

    @Override
    public int getItemCount() {
        return homeRecommendModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView homeHolRecommendImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            homeHolRecommendImageView = itemView.findViewById(R.id.rec_home_recommend_hol_item);
        }
    }
}
