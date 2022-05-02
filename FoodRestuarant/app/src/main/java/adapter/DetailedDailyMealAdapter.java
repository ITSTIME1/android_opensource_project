package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrestuarant.DetailedDailyMealActivity;
import com.example.foodrestuarant.R;

import java.util.List;

import model.DetailedDailyMealModel;

public class DetailedDailyMealAdapter extends RecyclerView.Adapter<DetailedDailyMealAdapter.ViewHolder> {

    Context context;
    List<DetailedDailyMealModel> detailedDailyMealModelList;

    public DetailedDailyMealAdapter(Context context, List<DetailedDailyMealModel> detailedDailyMealModelList) {
        this.context = context;
        this.detailedDailyMealModelList = detailedDailyMealModelList;
    }

    @NonNull
    @Override
    public DetailedDailyMealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.detailed_daily_meal_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DetailedDailyMealAdapter.ViewHolder holder, int position) {
        holder.detailedDailyMealImageView.setImageResource(detailedDailyMealModelList.get(position).getDetailedDailyMealImage());
        holder.detailedDailyMealNameView.setText(detailedDailyMealModelList.get(position).getDetailedDailyMealName());
        holder.detailedDailyMealDescriptionView.setText(detailedDailyMealModelList.get(position).getDetailedDailyMealDescription());
    }

    @Override
    public int getItemCount() {
        return detailedDailyMealModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView detailedDailyMealImageView;
        TextView detailedDailyMealNameView;
        TextView detailedDailyMealDescriptionView;

        // ViewHolder Constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            detailedDailyMealImageView = itemView.findViewById(R.id.detailed_item_image);
            detailedDailyMealNameView = itemView.findViewById(R.id.detailed_item_name);
            detailedDailyMealDescriptionView = itemView.findViewById(R.id.detailed_item_description);
        }

    }
}
