package com.example.newweatherscreen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    Button seoul_button;
    HorizontalScrollView horizontalScrollView;
    ImageView second_view;
    ImageView first_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);


        seoul_button = (Button) findViewById(R.id.seoul_button);
        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.scrollview);
        second_view = (ImageView) findViewById(R.id.second_view);
        first_view = (ImageView) findViewById(R.id.first_view);

        seoul_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 전체 horizontalScrollView 의 Width 길이 - 원하고자 하는 Second_View의 width /2 로 나눈 값만큼 이동시킴
                horizontalScrollView.scrollTo(horizontalScrollView.getWidth()  - second_view.getWidth() / 2, 0);
            }
        });
    }
}
