package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrestuarant.R;

import java.util.List;

import model.HomeVerModel;

public class HomeVerAdapter extends RecyclerView.Adapter<HomeVerAdapter.ViewHolder> {
    Context context;
    List<HomeVerModel> homeVerModelList;

    public HomeVerAdapter(Context context, List<HomeVerModel> homeVerModelList) {
        this.context = context;
        this.homeVerModelList = homeVerModelList;
    }

    @NonNull
    @Override
    public HomeVerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder verRecView = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_vertical_item, parent, false));
        return verRecView;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeVerAdapter.ViewHolder holder, int position) {
        holder.homeVerRecImageView.setImageResource(homeVerModelList.get(position).getVerImage());
        holder.homeVerRecFoodNameView.setText(homeVerModelList.get(position).getVerFoodName());
        holder.homeVerRecFoodDescriptionView.setText(homeVerModelList.get(position).getVerFoodDescription());

    }

    @Override
    public int getItemCount() {
        return homeVerModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView homeVerRecImageView;
        TextView homeVerRecFoodNameView;
        TextView homeVerRecFoodDescriptionView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            homeVerRecImageView = itemView.findViewById(R.id.home_ver_rec_item_image);
            homeVerRecFoodNameView = itemView.findViewById(R.id.home_ver_rec_item_name);
            homeVerRecFoodDescriptionView = itemView.findViewById(R.id.home_ver_rec_item_description);
        }
    }
}
