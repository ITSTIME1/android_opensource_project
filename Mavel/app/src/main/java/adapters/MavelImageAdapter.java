package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mavel.R;

import java.util.List;

import model.MavelRecItemModel;

public class MavelImageAdapter extends RecyclerView.Adapter<MavelImageAdapter.ViewHolder> {
    List<MavelRecItemModel> mavelRecItemModelList;

    public MavelImageAdapter(List<MavelRecItemModel> mavelRecItemModelList) {
        this.mavelRecItemModelList = mavelRecItemModelList;
    }

    @NonNull
    @Override
    public MavelImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mavel_image_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MavelImageAdapter.ViewHolder holder, int position) {
        holder.mavelImageView.setImageResource(mavelRecItemModelList.get(position).getMavelImage());

    }

    @Override
    public int getItemCount() {
        return mavelRecItemModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mavelImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mavelImageView = itemView.findViewById(R.id.mavel_image);
        }
    }
}
