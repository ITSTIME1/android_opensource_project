package adapters;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.HomeActivity;
import com.example.food.R;

import java.util.ArrayList;
import java.util.List;

import model.HomeHorizontalItemModel;
import model.HomeVerticalItemModel;


// * Adapter 같은 경우 RecyclerView.Adapter 를 상속해서 사용한다.
// * 필요에 따라 ViewHolder를 생성하고 관리한다.

// ** Flow 이해
// 1. 화면에 표시할 itemView를 정의해준다(layout)
// 2. RecyclerView는 아이템을 표시하기 위해서 가공과정을 거쳐야 하는데 Adapter, ViewHolder를 사용해주어야한다.
// 3. ViewHolder는 아이템을 정의할 객체이다. 각 개별 요소(아이템)들은 ViewHolder 객체로 정의되어진다.
// 4. 그렇게 ViewHolder에서는 RecyclerView에 의해서 표시될 View의 정보들을 담고 있다.
// 5. Adapter는 리사이클러뷰에 표시될 아이템 뷰를 생성하는 역할을 한다. 사용자의 리소스로부터 가공하는 작업을 Adapter에서 한다.
// ex) ViewHolder에 화면에 표시될 아이템 뷰를 저장 그리고 onCreateViewHolder를 통해서 화면에 표시될 View의 레이아웃을 담는다.


public class HomeHorizontalItemAdapter extends RecyclerView.Adapter<HomeHorizontalItemAdapter.ViewHolder> {
    UpdateVerticalRec updateVerticalRec;
    Activity activity;
    ArrayList<HomeHorizontalItemModel> homeItemModelList;

    boolean check = true;
    boolean select = true;
    int select_index = -1;

    public HomeHorizontalItemAdapter(UpdateVerticalRec updateVerticalRec, Activity activity, ArrayList<HomeHorizontalItemModel> homeItemModelList) {
        this.updateVerticalRec = updateVerticalRec;
        this.activity = activity;
        this.homeItemModelList = homeItemModelList;
    }

    @NonNull
    @Override
    // 이 메서드의 역할은 RecyclerView는 ViewHolder를 새로 만들어야될 때마다 이 메서드를 호출합니다.
    // 하지만 호출한다고 데이터가 저장이 되는게 아니라 ViewHolder에 연결된 View의 데이터 값들을 초기화 해줍니다.
    // 데이터가 저장이 안된 이유는 ViewHolder가 아직 특정 데이터에 바인딩 된 상태가 아니기 때문입니다.
    // 즉 onCreateViewHolder 메서드가 생성되고 난 이후에 onBindViewHolder에서 View를 연결해주는 작업을 합니다.
    public HomeHorizontalItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Context context = new parent.getContext();
//        // inflate 는 XML에 정의한 레이아웃들을 객체화 시키는 것이다.
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.home_horizontal_card_item, parent, false);
        // inflate 를 통해서 레이아웃이 객체화 되었으니 그 객체를 ViewHolder에 넘겨주고 ViewHolder 가 View를 가지고 있게끔 한다.
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_horizontal_card_item, parent, false));
    }


    // Bind Data into ViewHolder
    // RecyclerView는 ViewHolder를 데이터와 연결할 때 이 메서드를 호출합니다.
    // 새로 생긴 ViewHolder라는 것에 데이터를 가져와서 데이터를 ViewHolder의 레이아웃에 맞게 채워줍니다.
    @Override
    public void onBindViewHolder(@NonNull HomeHorizontalItemAdapter.ViewHolder holder, int position) {
        // 직접적으로 이제 뷰홀더를 데이터에 바인딩 작업을 합니다.
        holder.imageView.setImageResource(homeItemModelList.get(position).getImage());
        holder.textView.setText(homeItemModelList.get(position).getName());


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ViewHolder 가 View를 가지고 있기 때문에 View의 position 값을 가져옵니다.
                // holder를 클릭할때마다 notifyDataSetChanged()로 데이터가 변경된걸 RecyclerView에 전달합니다.
                select_index = holder.getAdapterPosition();
                notifyDataSetChanged();
                if(select_index == 3) {
                    ArrayList<HomeVerticalItemModel> homeVerticalItemModelList = new ArrayList<>();
                    homeVerticalItemModelList.add(new HomeVerticalItemModel(R.drawable.food1, "Salad", "Fresh Salad"));
                    homeVerticalItemModelList.add(new HomeVerticalItemModel(R.drawable.food2, "Salad", "Fresh Salad"));
                    homeVerticalItemModelList.add(new HomeVerticalItemModel(R.drawable.food3, "Salad", "Fresh Salad"));
                    homeVerticalItemModelList.add(new HomeVerticalItemModel(R.drawable.food2, "Salad", "Fresh Salad"));

                    updateVerticalRec.callBack(select_index, homeVerticalItemModelList);
                    select=false;

                }
                else if(select_index == 1) {
                    ArrayList<HomeVerticalItemModel> homeVerticalItemModelList = new ArrayList<>();
                    homeVerticalItemModelList.add(new HomeVerticalItemModel(R.drawable.fruits, "Fruits", "Fresh Fruits"));
                    homeVerticalItemModelList.add(new HomeVerticalItemModel(R.drawable.fruits1, "Fruits", "Fresh Fruits"));
                    homeVerticalItemModelList.add(new HomeVerticalItemModel(R.drawable.fruits2, "Fruits", "Fresh Fruits"));
                    homeVerticalItemModelList.add(new HomeVerticalItemModel(R.drawable.fruits, "Fruits", "Fresh Fruits"));

                    updateVerticalRec.callBack(select_index, homeVerticalItemModelList);
                    select=false;
                }
                else if(select_index == 2) {
                    ArrayList<HomeVerticalItemModel> homeVerticalItemModelList = new ArrayList<>();
                    homeVerticalItemModelList.add(new HomeVerticalItemModel(R.drawable.snacks, "Snacks", "Crisp Fruits"));
                    homeVerticalItemModelList.add(new HomeVerticalItemModel(R.drawable.snacks1, "Snacks", "Crsip Fruits"));
                    homeVerticalItemModelList.add(new HomeVerticalItemModel(R.drawable.snacks2, "Snacks", "Crsip Fruits"));
                    homeVerticalItemModelList.add(new HomeVerticalItemModel(R.drawable.snacks, "Snacks", "Crsip Fruits"));

                    updateVerticalRec.callBack(select_index, homeVerticalItemModelList);
                }
                else if(select_index == 0) {
                    ArrayList<HomeVerticalItemModel> homeVerticalItemModelList = new ArrayList<>();
                    homeVerticalItemModelList.add(new HomeVerticalItemModel(R.drawable.snacks, "Hambuger", "Delicious Fruits"));
                    homeVerticalItemModelList.add(new HomeVerticalItemModel(R.drawable.snacks1, "Hambuger", "Delicious Fruits"));
                    homeVerticalItemModelList.add(new HomeVerticalItemModel(R.drawable.snacks2, "Hambuger", "Delicious Fruits"));
                    homeVerticalItemModelList.add(new HomeVerticalItemModel(R.drawable.snacks, "Hambuger", "Delicious Fruits"));

                    updateVerticalRec.callBack(select_index, homeVerticalItemModelList);
                    select=false;
                }


            }
        });
        if(select) {
            holder.cardView.setBackgroundResource(R.drawable.default_bg);
        }
        else {
            if(select_index == position) {
                holder.cardView.setBackgroundResource(R.drawable.changed_bg);
            } else {
                holder.cardView.setBackgroundResource(R.drawable.default_bg);
            }
        }
    }



    // DataList total Length
    // Item의 모든 데이터개수를 반환해줍니다.
    @Override
    public int getItemCount() {
        return homeItemModelList.size();
    }
    // ViewHolder 는 화면에 표시될 아이템 뷰를 저장하는 객체(공간).
    // 즉 이곳엔 사용할 아이템들을 담아준다.
    public class ViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder 내부에서 표시할 아이템 뷰를 정의한다.
        ImageView imageView;
        TextView textView;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.horizontal_card_view);
            imageView = itemView.findViewById(R.id.food_view);
            textView = itemView.findViewById(R.id.text_view);
        }
    }


}
