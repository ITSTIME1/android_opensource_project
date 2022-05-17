package com.example.mavel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

/**
 * [DetailActivity]
 *
 * In this page show detail marvelMovieList content of MainActivity
 *
 */
public class DetailActivity extends AppCompatActivity {

    protected ImageView detailImageView;
    protected TextView detailMovieTitle;
    protected TextView detailReleaseTime;
    protected TextView detailPopularity;
    protected TextView detailAverage;
    protected TextView detailOverView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();
        getIntentData();


    }

    /**
     * [Detail Reference]
     */
    public void init() {
        detailImageView = findViewById(R.id.detail_image);
        detailMovieTitle = findViewById(R.id.detail_title);
        detailReleaseTime = findViewById(R.id.detail_releaseDateTime);
        detailPopularity = findViewById(R.id.detail_popularity);
        detailAverage = findViewById(R.id.detail_average);
        detailOverView = findViewById(R.id.detail_overView);

    }

    /**
     * [Get retrofit data from mainActivity]
     */
    @SuppressLint("SetTextI18n")
    public void getIntentData() {

        Intent fromMainActivityIntent = getIntent();
        String detail_image_data = fromMainActivityIntent.getStringExtra("detail_Image");
        String detail_title_data = fromMainActivityIntent.getStringExtra("detail_title");
        String detail_releaseTime_data = fromMainActivityIntent.getStringExtra("detail_releaseTime");
        Double detail_average_data = fromMainActivityIntent.getDoubleExtra("detail_average", 0);
        String detail_overView_data = fromMainActivityIntent.getStringExtra("detail_overView");
        Double detail_popularity_data = fromMainActivityIntent.getDoubleExtra("detail_popularity", 0);


        // String type
        Glide.with(this).load("https://image.tmdb.org/t/p/w500/"+detail_image_data).into(detailImageView);
        detailMovieTitle.setText(detail_title_data);
        detailReleaseTime.setText(detail_releaseTime_data);
        detailOverView.setText(detail_overView_data);

        // Double type
        detailAverage.setText(Double.toString(detail_average_data));
        detailPopularity.setText(Double.toString(detail_popularity_data));
    }


}

