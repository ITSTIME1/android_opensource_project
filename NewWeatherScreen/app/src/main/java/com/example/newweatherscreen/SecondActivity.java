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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);


        seoul_button = (Button) findViewById(R.id.seoul_button);
        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.scrollview);

        seoul_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                horizontalScrollView.pageScroll(1);
            }
        });
    }
}
