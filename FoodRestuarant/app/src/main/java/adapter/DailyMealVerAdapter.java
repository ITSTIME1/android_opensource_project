package adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrestuarant.R;

import java.util.List;

import model.DailyMealModel;

public class DailyMealVerAdapter extends RecyclerView.Adapter<DailyMealVerAdapter.ViewHolder> {

    List<DailyMealModel> dailyMealModelList;
    Context context;

    public DailyMealVerAdapter(List<DailyMealModel> dailyMealModelList, Context context) {
        this.dailyMealModelList = dailyMealModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public DailyMealVerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_meal_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DailyMealVerAdapter.ViewHolder holder, int position) {
        holder.dailyImageView.setImageResource(dailyMealModelList.get(position).getDailyMealImage());
        holder.dailyNameView.setText(dailyMealModelList.get(position).getDailyMealName());
        holder.dailyDescriptionView.setText(dailyMealModelList.get(position).getDailyMealDescription());

    }

    @Override
    public int getItemCount() {
        return dailyMealModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView dailyImageView;
        TextView dailyNameView;
        TextView dailyDescriptionView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dailyImageView = itemView.findViewById(R.id.daily_meal_item_first);
            dailyNameView = itemView.findViewById(R.id.daily_meal_name_view);
            dailyDescriptionView = itemView.findViewById(R.id.daily_meal_description_view);
        }
    }
}
