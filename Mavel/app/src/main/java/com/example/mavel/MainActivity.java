package com.example.mavel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import adapters.MavelImageAdapter;
import me.relex.circleindicator.CircleIndicator2;
import me.relex.circleindicator.CircleIndicator3;
import model.MavelRecItemModel;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<MavelRecItemModel> mavelRecItemModelList;
    private MavelImageAdapter mavelImageAdapter;
    private CircleIndicator2 circleIndicator2;
    private PagerSnapHelper pagerSnapHelper;
    private RecyclerViewDecoration recyclerViewDecoration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.mavel_rec);


        circleIndicator2 = findViewById(R.id.indicator);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        recyclerViewDecoration = new RecyclerViewDecoration( recyclerView.getWidth() / 2);
        recyclerView.addItemDecoration(recyclerViewDecoration);

        mavelRecItemModelList = new ArrayList<>();
        mavelRecItemModelList.add(new MavelRecItemModel(R.drawable.mavelimage1));
        mavelRecItemModelList.add(new MavelRecItemModel(R.drawable.mavelimage1));
        mavelRecItemModelList.add(new MavelRecItemModel(R.drawable.mavelimage1));
        mavelRecItemModelList.add(new MavelRecItemModel(R.drawable.mavelimage1));
        mavelRecItemModelList.add(new MavelRecItemModel(R.drawable.mavelimage1));
        mavelRecItemModelList.add(new MavelRecItemModel(R.drawable.mavelimage1));


        mavelImageAdapter = new MavelImageAdapter(mavelRecItemModelList);
        recyclerView.setAdapter(mavelImageAdapter);

        pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recyclerView);
        circleIndicator2.attachToRecyclerView(recyclerView, pagerSnapHelper);




        recyclerView.setHasFixedSize(true);
    }



    // ** HomeHolRec 아이템 간격 조정 **
    public class RecyclerViewDecoration extends RecyclerView.ItemDecoration {
        private final int divWidth;

        public RecyclerViewDecoration(int divWidth)
        {
            this.divWidth = divWidth;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
        {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.right = divWidth;
        }
    }
}