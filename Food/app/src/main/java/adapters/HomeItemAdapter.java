package adapters;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.R;

import java.util.List;

import model.HomeItemModel;


// * Adapter 같은 경우 RecyclerView.Adapter 를 상속해서 사용한다.
// * 필요에 따라 ViewHolder를 생성하고 관리한다.

// ** Flow 이해
// 1. 화면에 표시할 itemView를 정의해준다(layout)
// 2. RecyclerView는 아이템을 표시하기 위해서 가공과정을 거쳐야 하는데 Adapter, ViewHolder를 사용해주어야한다.
// 3. ViewHolder는 아이템을 정의할 객체이다. 각 개별 요소(아이템)들은 ViewHolder 객체로 정의되어진다.
// 4. 그렇게 ViewHolder에서는 RecyclerView에 의해서 표시될 View의 정보들을 담고 있다.
// 5. Adapter는 리사이클러뷰에 표시될 아이템 뷰를 생성하는 역할을 한다. 사용자의 리소스로부터 가공하는 작업을 Adapter에서 한다.
// ex) ViewHolder에 화면에 표시될 아이템 뷰를 저장 그리고 onCreateViewHolder를 통해서 화면에 표시될 View의 레이아웃을 담는다.


public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.ViewHolder> {
    //
    Context context;
    List<HomeItemModel> list;

    public HomeItemAdapter(Context context, List<HomeItemModel> list){
        this.context = context;
        this.list = list;
    }

    // ViewHolder 는 화면에 표시될 아이템 뷰를 저장하는 객체(공간).
    // 즉 이곳엔 사용할 아이템들을 담아준다.
    public class ViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder 내부에서 표시할 아이템 뷰를 정의한다.
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.food_view);
            textView = itemView.findViewById(R.id.text_view);
        }
    }


    @NonNull
    @Override
    // 이 메서드의 역할은 RecyclerView는 ViewHolder를 새로 만들어야될 때마다 이 메서드를 호출합니다.
    // 하지만 호출한다고 데이터가 저장이 되는게 아니라 ViewHolder에 연결된 View의 데이터 값들을 초기화 해줍니다.
    // 데이터가 저장이 안된 이유는 ViewHolder가 아직 특정 데이터에 바인딩 된 상태가 아니기 때문입니다.
    // 즉 onCreateViewHolder 메서드가 생성되고 난 이후에 onBindViewHolder에서 View를 연결해주는 작업을 합니다.
    public HomeItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_horizontal_card_item, parent, false));
    }


    // Bind Data into ViewHolder
    // RecyclerView는 ViewHolder를 데이터와 연결할 때 이 메서드를 호출합니다.
    // 새로 생긴 ViewHolder라는 것에 데이터를 가져와서 데이터를 ViewHolder의 레이아웃에 맞게 채워줍니다.
    @Override
    public void onBindViewHolder(@NonNull HomeItemAdapter.ViewHolder holder, int position) {
        // 직접적으로 이제 뷰홀더를 데이터에 바인딩 작업을 합니다.
        holder.imageView.setImageResource(list.get(position).getImage());
        holder.textView.setText(list.get(position).getName());
    }


    // DataList total Length
    // Item의 모든 데이터개수를 반환해줍니다.
    @Override
    public int getItemCount() {
        return list.size();
    }


}
