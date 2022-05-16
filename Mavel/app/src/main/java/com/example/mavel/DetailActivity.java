package com.example.mavel;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * [DetailActivity]
 *
 * In this page show detail marvelMovieList content of MainActivity
 *
 */
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    /**
     * [Detail Reference]
     */
    public void init() {
        ImageView detailImageView = findViewById(R.id.detail_image);
        TextView detailMovieTitle = findViewById(R.id.detail_title);
        TextView detailReleaseTime = findViewById(R.id.detail_releaseDateTime);
        TextView detailPopularity = findViewById(R.id.detail_popularity);
        TextView detailAverage = findViewById(R.id.detail_average);
        TextView detailOverView = findViewById(R.id.detail_overView);
    }

    public void getData() {}
}

