package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrestuarant.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import model.HomeVerModel;

public class HomeVerAdapter extends RecyclerView.Adapter<HomeVerAdapter.ViewHolder>{
    // Fragement 에서 context 값을 얻어오기 위해서 getActivity()값을 사용한다.
    // 만약 이게 Activity가 아니고 Context 값을 인자로 넘겨받는다면 null을 반환 할 수 있다.
    Activity activity;
    // 이전에 List를 사용했을때는
    // 각 요소가 객체이고 해당 위치에 엑세스 되는 순서를 가지는 요소라면
    // 이 ArrayList 는 동적으로 증가, 감소 시킬 수 있는 리스트이다.
    ArrayList<HomeVerModel> homeVerModelList;

    public HomeVerAdapter(Activity activity, ArrayList<HomeVerModel> homeVerModelList) {
        this.activity = activity;
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

        // setImageResource 는 int resID를 인자로 받기 때문에 homeVerModelList에 있는 위치의 이미지 값을 가져오고 그 값을 인트형식으로 반환해준다.
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
