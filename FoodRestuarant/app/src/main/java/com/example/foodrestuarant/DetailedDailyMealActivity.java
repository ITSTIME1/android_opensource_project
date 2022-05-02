package com.example.foodrestuarant;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapter.DetailedDailyMealAdapter;
import model.DetailedDailyMealModel;

public class DetailedDailyMealActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<DetailedDailyMealModel> detailedDailyMealModelList;
    DetailedDailyMealAdapter detailedDailyMealAdapter;
    ImageView activityImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_dailymeal);

        String type = getIntent().getStringExtra("type");
        activityImage = findViewById(R.id.detailed_daily_meal_main_image);

        // detailed dailyMeal refer
        recyclerView = findViewById(R.id.detailed_daily_meal_rec);
        // recyclerView set up layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // detailedDailyMealList refer
        detailedDailyMealModelList = new ArrayList<>();
        // from DetailedDailyMealAdapter to this activity deliver data
        detailedDailyMealAdapter = new DetailedDailyMealAdapter(this, detailedDailyMealModelList);
        recyclerView.setAdapter(detailedDailyMealAdapter);

        if(type != null && type.equalsIgnoreCase("breakfast")){
            detailedDailyMealModelList.add(new DetailedDailyMealModel(R.drawable.breakfast1, "BreakFast", "very good"));
            detailedDailyMealModelList.add(new DetailedDailyMealModel(R.drawable.breakfast1, "NiceFast", "very good" ));
            detailedDailyMealModelList.add(new DetailedDailyMealModel(R.drawable.breakfast1, "GoodFast", "very good"));
            detailedDailyMealModelList.add(new DetailedDailyMealModel(R.drawable.breakfast1, "GoodFast", "very good"));
            detailedDailyMealModelList.add(new DetailedDailyMealModel(R.drawable.breakfast1, "GoodFast", "very good"));
            detailedDailyMealModelList.add(new DetailedDailyMealModel(R.drawable.breakfast1, "GoodFast", "very good"));

            detailedDailyMealAdapter.notifyDataSetChanged();
        }

    }
}
