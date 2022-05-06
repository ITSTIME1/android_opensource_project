package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;

import java.util.List;

import Models.HomeAdvertiseModel;

public class HomeHolAdverAdapter extends RecyclerView.Adapter<HomeHolAdverAdapter.ViewHolder> {

    private List<HomeAdvertiseModel> homeAdvertiseModelList;
    private Context context;

    public HomeHolAdverAdapter(List<HomeAdvertiseModel> homeAdvertiseModelList, Context context) {
        this.homeAdvertiseModelList = homeAdvertiseModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeHolAdverAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_home_ads_hol_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHolAdverAdapter.ViewHolder holder, int position) {
        holder.homeHolAdvertiseImageView.setImageResource(homeAdvertiseModelList.get(position).getHomeAdvertiseImage());

    }

    @Override
    public int getItemCount() {
        return homeAdvertiseModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView homeHolAdvertiseImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            homeHolAdvertiseImageView = itemView.findViewById(R.id.rec_home_ads_hol_item);
        }
    }
}
