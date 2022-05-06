package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrestuarant.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import model.HomeVerModel;

public class HomeVerAdapter extends RecyclerView.Adapter<HomeVerAdapter.ViewHolder>{
    // Fragement 에서 context 값을 얻어오기 위해서 getActivity()값을 사용한다.
    // 만약 이게 Activity가 아니고 Context 값을 인자로 넘겨받는다면 null을 반환 할 수 있다.
    private BottomSheetDialog bottomSheetDialog;
    Context context;
    // 이전에 List를 사용했을때는
    // 각 요소가 객체이고 해당 위치에 엑세스 되는 순서를 가지는 요소라면
    // 이 ArrayList 는 동적으로 증가, 감소 시킬 수 있는 리스트이다.
    ArrayList<HomeVerModel> homeVerModelList;

    public HomeVerAdapter(Context context, ArrayList<HomeVerModel> homeVerModelList) {
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

        // viewholder에 데이터를 주입시킬때 bimage, bname,bdescription 변수는 각각의 list를 담고있는 곳에서 각각의 요소를 따오고
        // 이후 ver_holder를 눌렀을때 dialog를 표시해주기 위해서 사용되는 변수이다. 즉 다이얼로그가 떴을때 해당 viewholder가 가지고 있는 view의 정보들을 가지고 오기 위해서
        // 만들어진 변수이다.


        // 밑에 holder.은 viewholder에 view를 바인딩 시키기 위한 변수들이다.


        // 그리고 나서 이후에 있는 holder.iteview를 클릭했을때
        // adapter 가지고 있는 bottomdialog의 스타일을 부여해주고
        // sheetView에는 bottomDialog가 어떤 View객체로 표햔될건지 미리 정의해두었던 xml 파일을 infalte를 통해서 객체를 연결시켜준다.
        // 연결시켜주면 sheetView에는 dialog에 뜰 view객체가 들어있다.

        // 이후에 sheetView를 클릭했을 때 Toastmessage로 클릭했다는걸 보여주고 창을 내려주는 dismiss()를 사용하게 해준다.
        // 우선 그 밖 변수들은 holder 를 클릭했을때 이전에 만들어두었던 view bottomsheetdialog에 어떤 정보를 표시해줄지 표현해주는 것이다.
        // 그리고 그걸 보여주는 setContextView infalter 시킨다.

        final int bImage = homeVerModelList.get(position).getVerImage();
        final String bName = homeVerModelList.get(position).getVerFoodName();
        final String bDescription = homeVerModelList.get(position).getVerFoodDescription();

        // setImageResource 는 int resID를 인자로 받기 때문에 homeVerModelList에 있는 위치의 이미지 값을 가져오고 그 값을 인트형식으로 반환해준다.
        holder.homeVerRecImageView.setImageResource(homeVerModelList.get(position).getVerImage());
        holder.homeVerRecFoodNameView.setText(homeVerModelList.get(position).getVerFoodName());
        holder.homeVerRecFoodDescriptionView.setText(homeVerModelList.get(position).getVerFoodDescription());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bottomSheetDialog = new BottomSheetDialog(context, com.google.android.material.R.style.Theme_Design_BottomSheetDialog);

                View sheetView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_dialog, null);

                sheetView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "ShowDialog", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                    }
                });

                ImageView bottomImg = sheetView.findViewById(R.id.bottom_sheet_lin_imageView);
                TextView bottomName = sheetView.findViewById(R.id.bottom_sheet_lin_logoNameView);
                TextView bottomDescription = sheetView.findViewById(R.id.bottom_sheet_lin_descriptionView);


                bottomImg.setImageResource(bImage);
                bottomName.setText(bName);
                bottomDescription.setText(bDescription);

                bottomSheetDialog.setContentView(sheetView);
                bottomSheetDialog.show();
                // Image or TextView 참조해서 setText 혹은 setImageResource이용해서 텍스트랑 이미지 연결시켜줌.
                // ItemView를 클릭하면 bottomSheet dialog가 뜨게 되고 bottom_sheet_dialog를 infalter를 통해서 xml을 View클래스 토대로 만들어서 줌 그럼 그 레이아웃을 클릭했을 경우엔
                // Toast메세지로 showDialog가 뜬다고 함.

            }
        });
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
