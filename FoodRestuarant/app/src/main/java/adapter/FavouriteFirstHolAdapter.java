package adapter;

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

import model.FavouriteHolFirstModel;

// 상속받자
public class FavouriteFirstHolAdapter extends RecyclerView.Adapter<FavouriteFirstHolAdapter.ViewHolder>{

    List<FavouriteHolFirstModel> favouriteHolFirstModelList;

    public FavouriteFirstHolAdapter(List<FavouriteHolFirstModel> favouriteHolFirstModelList) {
        this.favouriteHolFirstModelList = favouriteHolFirstModelList;
    }

    // 부모에서 받아온 값을 재정의하자
    @NonNull
    @Override
    public FavouriteFirstHolAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_hor_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteFirstHolAdapter.ViewHolder holder, int position) {
        holder.favImageView.setImageResource(favouriteHolFirstModelList.get(position).getFavFirstHolImage());
        holder.favNameView.setText(favouriteHolFirstModelList.get(position).getFavFirstHolName());
        holder.favDescriptionView.setText(favouriteHolFirstModelList.get(position).getFavFirstHolDescription());
    }

    @Override
    public int getItemCount() {
        return favouriteHolFirstModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder의 생성자
        // 뷰 클래스를 인자값으로 받음
        // 뷰를 연결하기 위해서 인자값을 통해서 뷰를 설정해줌.
        ImageView favImageView;
        TextView favNameView;
        TextView favDescriptionView;

        public ViewHolder(@NonNull View itemView) {
            // super() 메서드를 통해서 부모에 있는 생성자를 가지고오자
            super(itemView);
            favImageView = itemView.findViewById(R.id.favourite_hor_item_image);
            favNameView = itemView.findViewById(R.id.favourite_hor_item_name);
            favDescriptionView = itemView.findViewById(R.id.favourite_hor_item_description);
        }
    }
}
