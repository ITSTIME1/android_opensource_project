package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.R;

import java.util.List;

import model.HomeVerticalItemModel;

public class HomeVerticalItemAdapter extends RecyclerView.Adapter<HomeVerticalItemAdapter.ViewHolder> {

    Context context;
    List<HomeVerticalItemModel> homeVerticalItemModelList;

    public HomeVerticalItemAdapter(Context context, List<HomeVerticalItemModel> homeVerticalItemModelList){
        this.context = context;
        this.homeVerticalItemModelList = homeVerticalItemModelList;
    }


    // VerticalItem Object
    // 뷰를 가지고 있는 친구.
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView home_vertical_item_image_view;
        TextView name_text_view;
        TextView description_text_view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_text_view = itemView.findViewById(R.id.home_vertical_item_name);
            description_text_view = itemView.findViewById(R.id.home_vertical_item_description);
            home_vertical_item_image_view = itemView.findViewById(R.id.home_vertical_item_image);
        }
    }


    // ViewHolder object create
    // 레이아웃들을 객체화 시키기 위해서 inflate를 사용한다.
    // setContentView() 함수가 바로 Layout을 객체화 시키는 함수다.
    @NonNull
    @Override
    public HomeVerticalItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_vertical_card_item, parent, false));
    }

    // View binding
    @Override
    public void onBindViewHolder(@NonNull HomeVerticalItemAdapter.ViewHolder holder, int position) {
        holder.home_vertical_item_image_view.setImageResource(homeVerticalItemModelList.get(position).getImage());
        holder.name_text_view.setText(homeVerticalItemModelList.get(position).getVertical_item_name());
        holder.description_text_view.setText(homeVerticalItemModelList.get(position).getVertical_item_description());
    }


    // Return total verticalItem list length
    @Override
    public int getItemCount() {
        return homeVerticalItemModelList.size();
    }

}
