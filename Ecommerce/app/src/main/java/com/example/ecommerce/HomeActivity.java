package com.example.ecommerce;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Adapters.HomeHolRecoAdapter;
import Models.HomeRecommendModel;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView homeHolRecommendRec;
    private ArrayList<HomeRecommendModel> homeRecommendModelArrayList;
    private HomeHolRecoAdapter homeHolRecoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        homeHolRecommendRec = (RecyclerView) findViewById(R.id.home_recommend_hol_rec);
        homeHolRecommendRec.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        homeRecommendModelArrayList = new ArrayList<>();
        homeRecommendModelArrayList.add(new HomeRecommendModel(R.drawable.recom));
        homeRecommendModelArrayList.add(new HomeRecommendModel(R.drawable.recomtwo));
        homeRecommendModelArrayList.add(new HomeRecommendModel(R.drawable.recomthree));
        homeRecommendModelArrayList.add(new HomeRecommendModel(R.drawable.recomfour));

        homeHolRecoAdapter = new HomeHolRecoAdapter(homeRecommendModelArrayList, this);
        homeHolRecommendRec.setAdapter(homeHolRecoAdapter);

        homeHolRecommendRec.setNestedScrollingEnabled(false);

    }
}
