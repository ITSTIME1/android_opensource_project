package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrestuarant.R;

import java.util.List;

import model.FavouriteVerFirstModel;

public class FavouriteFirstVerAdapter extends RecyclerView.Adapter<FavouriteFirstVerAdapter.ViewHolder>{

    List<FavouriteVerFirstModel> favouriteVerFirstModelList;

    public FavouriteFirstVerAdapter(List<FavouriteVerFirstModel> favouriteVerFirstModelList) {
        this.favouriteVerFirstModelList = favouriteVerFirstModelList;
    }

    @NonNull
    @Override
    public FavouriteFirstVerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // ViewGrop == RecyclerView를 기본적으로 가리키는 것이고
        // LayoutInflater는 xml에 정의해둔 fragment_favourite_ver_item을 실제 메모리에 올리는 역할을한다.
        // LayoutInflater 는 xml resource를 View객체로 반환한다.
        // parent인 ViewGroup이 바뀔 수 있음 리니어레이아웃 리사이클러뷰 그래서 그걸 가르킨 내용들을 전부 가져와서 객체화 시킴.
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_favourite_ver_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteFirstVerAdapter.ViewHolder holder, int position) {

        holder.favFirstVerImageView.setImageResource(favouriteVerFirstModelList.get(position).getFavFirstVerImage());
        holder.favFirstVerNameView.setText(favouriteVerFirstModelList.get(position).getFavFirstVerName());
        holder.favFirstVerDescriptionView.setText(favouriteVerFirstModelList.get(position).getFavFirstVerDescription());

    }

    @Override
    public int getItemCount() {
        return favouriteVerFirstModelList.size();
    }


    // Favourite Vertical Item
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView favFirstVerImageView;
        TextView favFirstVerNameView;
        TextView favFirstVerDescriptionView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favFirstVerImageView = itemView.findViewById(R.id.favourite_ver_image_view);
            favFirstVerNameView = itemView.findViewById(R.id.favourite_ver_name_view);
            favFirstVerDescriptionView = itemView.findViewById(R.id.favourite_ver_description_view);
        }
    }
}
