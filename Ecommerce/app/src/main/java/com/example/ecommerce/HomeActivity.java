package com.example.ecommerce;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Adapters.HomeHolAdverAdapter;
import Adapters.HomeHolRecoAdapter;
import Models.HomeAdvertiseModel;
import Models.HomeRecommendModel;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView homeHolRecommendRec;
    private ArrayList<HomeRecommendModel> homeRecommendModelArrayList;
    private HomeHolRecoAdapter homeHolRecoAdapter;

    private RecyclerView homeHolAdsRec;
    private ArrayList<HomeAdvertiseModel> homeAdvertiseModelArrayList;
    private HomeHolAdverAdapter homeHolAdverAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        homeHolRecommendRec = (RecyclerView) findViewById(R.id.home_recommend_hol_rec);
        homeHolAdsRec = (RecyclerView) findViewById(R.id.home_advertise_ver_rec);


        homeHolRecommendRec.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        homeHolAdsRec.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        homeRecommendModelArrayList = new ArrayList<>();
        homeAdvertiseModelArrayList = new ArrayList<>();

        homeRecommendModelArrayList.add(new HomeRecommendModel(R.drawable.recom));
        homeRecommendModelArrayList.add(new HomeRecommendModel(R.drawable.recomtwo));
        homeRecommendModelArrayList.add(new HomeRecommendModel(R.drawable.recomthree));
        homeRecommendModelArrayList.add(new HomeRecommendModel(R.drawable.recomfour));

        homeAdvertiseModelArrayList.add(new HomeAdvertiseModel(R.drawable.adsone));
        homeAdvertiseModelArrayList.add(new HomeAdvertiseModel(R.drawable.adstwo));
        homeAdvertiseModelArrayList.add(new HomeAdvertiseModel(R.drawable.adsthree));
        homeAdvertiseModelArrayList.add(new HomeAdvertiseModel(R.drawable.adsfour));

        homeHolRecoAdapter = new HomeHolRecoAdapter(homeRecommendModelArrayList, this);
        homeHolAdverAdapter = new HomeHolAdverAdapter(homeAdvertiseModelArrayList, this);

        homeHolRecommendRec.setAdapter(homeHolRecoAdapter);
        homeHolAdsRec.setAdapter(homeHolAdverAdapter);


        homeHolRecommendRec.setNestedScrollingEnabled(false);
        homeHolAdsRec.setNestedScrollingEnabled(false);

    }
}
