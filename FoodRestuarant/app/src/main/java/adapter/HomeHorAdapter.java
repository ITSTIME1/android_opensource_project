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

import model.HomeHorModel;

public class HomeHorAdapter extends RecyclerView.Adapter<HomeHorAdapter.ViewHolder> {
    Context context;
    List<HomeHorModel> homeHorModelList;

    public HomeHorAdapter(Context context, List<HomeHorModel> homeHorModelList) {
        this.context = context;
        this.homeHorModelList = homeHorModelList;
    }

    // onCreateViewHolder
    // 데이터를 삽입시키지 않고 ViewHolder 하나의 객체만 만들어지는 상태가 여기서 만들어진다.
    // 즉 Inflater를 통해서 해당 recyclerview에 표시될 item의 layout을 뷰 홀더가 감싸고 있기 때문에 그걸 객체화 시켜서 RecyclerView에 보여주게 된다.
    // 그럴려면 객체화 시킨 뒤에 값을 넣어주고 그 값은 RecyclerView의 생명주기를 통해서 ViewHolder가 필요에 의해서(사용자 정의에) 의해서 생성이 되면
    // 그 이후에 있을 onBindViewHolder 메서드에서 값을 넣어준다.

    @NonNull
    @Override
    public HomeHorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // ViewGroup parent 의미하는 바는 RecyclerView는 ViewGroup의 포함되어 있는 한 종류.
        // 즉 parent는 RecyclerView를 의미하고 Inflater를 통해서 미리 만들어두었던 item의 layout들을 객체화 시켜서 하나의 ViewHolder를 만들어준다.
        ViewHolder verHolView = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_horizontal_item, parent, false));
        return verHolView;
    }

    // onBindViewHolder
    // After ViewHolder created 뷰 홀더 객체가 생성되고 난후 이곳에서 데이터가 입력되어지게 된다.
    @Override
    public void onBindViewHolder(@NonNull HomeHorAdapter.ViewHolder holder, int position) {
        // Holder
        // 객체화 시켜둔 레이아웃 객체 즉 ViewHolder를 가르키며
        // 그 홀더가 가지고 있는 homeRecImageView view의 imageResource 이미지 데이터를 homeHorModelList 리스트 형태의 데이터에서 순서대로 나열되어 있을때 position 위치값(index)을 가져와
        // getImage() 이미지 데이터를 넘겨준다.


        // 객체의 성질중 중요한 class의 멤버필드(변수)에 접근할때 getter 와 setter로만 접근을 하는걸 권장한다고 하고 있다.
        // 즉 HomeHorModel의 저장소에 있는 ImageView 값을 가져오기 위해 getter메서드를 사용했다.
        holder.homeHolRecImageView.setImageResource(homeHorModelList.get(position).getImage());
        holder.homeHolRecTextView.setText(homeHorModelList.get(position).getImgName());

    }

    @Override
    public int getItemCount() {
        return homeHorModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView homeHolRecImageView;
        TextView homeHolRecTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            homeHolRecImageView = itemView.findViewById(R.id.home_hor_rec_item_image);
            homeHolRecTextView = itemView.findViewById(R.id.home_hor_rec_item_name);
        }
    }
}
