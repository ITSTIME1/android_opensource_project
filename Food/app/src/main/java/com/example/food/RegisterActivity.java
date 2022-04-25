package com.example.food;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    Button register_complete;
    TextView signIn_page;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_view);


        register_complete = (Button) findViewById(R.id.Register_complete_button);
        signIn_page = (TextView) findViewById(R.id.SignIn_page_button);


        register_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home_page_intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(home_page_intent);
                finish();
            }
        });

        signIn_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signIn_page_intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(signIn_page_intent);
                finish();
            }
        });

    }
}
