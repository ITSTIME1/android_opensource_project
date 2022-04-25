package com.example.food;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapters.HomeItemAdapter;
import model.HomeItemModel;

public class HomeActivity extends AppCompatActivity {

    // recyclerView
    RecyclerView homeHorizontalRec;
    // itemlist
    List<HomeItemModel> homeItemModelList;
    // Adapter
    HomeItemAdapter homeItemAdapter;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_view);

        // Recycler Reference
        homeHorizontalRec = (RecyclerView) findViewById(R.id.home_item_rec);
        homeHorizontalRec.setLayoutManager(new LinearLayoutManager(HomeActivity.this, RecyclerView.HORIZONTAL, false));

        homeItemModelList = new ArrayList<>();
        // RecyclerView에 표시될 아이템
        homeItemModelList.add(new HomeItemModel(R.drawable.hamburger, "hambuger"));
        homeItemModelList.add(new HomeItemModel(R.drawable.hamburger, "hambuger"));
        homeItemModelList.add(new HomeItemModel(R.drawable.hamburger, "hambuger"));
        homeItemModelList.add(new HomeItemModel(R.drawable.hamburger, "hambuger"));




        // Adapter에 연결한다.
        homeItemAdapter = new HomeItemAdapter(this, homeItemModelList);
        homeHorizontalRec.setAdapter(homeItemAdapter);
        homeHorizontalRec.setHasFixedSize(true);
        homeHorizontalRec.setNestedScrollingEnabled(false);


        HomeRecyclerViewDecoration homeRecyclerViewDecoration = new HomeRecyclerViewDecoration(20, 50);
        homeHorizontalRec.addItemDecoration(homeRecyclerViewDecoration);



    }

    // 아이템 데코레이션 정의
    public class HomeRecyclerViewDecoration extends RecyclerView.ItemDecoration {
        private final int divHeight;
        private final int divWidth;

        public HomeRecyclerViewDecoration(int divHeight, int divWidth){
            this.divHeight = divHeight;
            this.divWidth = divWidth;
        }

        // Rect outRect =  default zero if not excute you have to set top, bottom, right, left
        // View = Parent child view
        // RecyclerView = decorating parent
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

            // view 에 포지션 값(0,1,2,3) 이 전체 아이템 개수 - 1(3) 값이랑 다르다면
            if(parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1){
                // 0 != 3
                // 1 != 3
                // 2 != 3

                // 한마디로 getChildAdapterPostion(view)의 Index 값과 총 getItemCount 를 리턴 받은 값과 비교를 계속 해가면서
                // 리스트에 있는 모든 값들에 적용을 시켜준다.
                outRect.bottom = divHeight;
                outRect.right = divWidth;
                System.out.println(parent.getAdapter().getItemCount()-1);

                System.out.print("=========");
                System.out.println(parent.getChildAdapterPosition(view));
            }
        }
    }
}
