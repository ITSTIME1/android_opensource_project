package com.example.newweatherscreen;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    Button tokyo_button;
    Button second_button;
    Button bamboo_button;
    Button jeju_button;
    HorizontalScrollView horizontalScrollView;
    ImageView second_view;
    ImageView first_view;
    ImageView three_view;
    ImageView last_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);


        tokyo_button = (Button) findViewById(R.id.tokyo_button);
        second_button = (Button) findViewById(R.id.second_button);
        bamboo_button = (Button) findViewById(R.id.bamboo_button);
        jeju_button = (Button) findViewById(R.id.jeju_button);
        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.scrollview);


        first_view = (ImageView) findViewById(R.id.first_view);
        second_view = (ImageView) findViewById(R.id.second_view);
        three_view = (ImageView) findViewById(R.id.three_view);
        last_view = (ImageView) findViewById(R.id.last_view);

        tokyo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 전체 horizontalScrollView 의 Width 길이 - 원하고자 하는 Second_View의 width /2 로 나눈 값만큼 이동시킴
                horizontalScrollView.scrollTo(0, 0);
            }
        });

        second_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                horizontalScrollView.scrollTo(horizontalScrollView.getWidth() - second_view.getWidth()/2, 0);
            }
        });

        bamboo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                horizontalScrollView.scrollTo(horizontalScrollView.getWidth() - (three_view.getWidth()/2 - last_view.getWidth()), 0);
            }
        });

        jeju_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                horizontalScrollView.scrollTo(last_view.getRight(), 0);
            }
        });

    }

}
