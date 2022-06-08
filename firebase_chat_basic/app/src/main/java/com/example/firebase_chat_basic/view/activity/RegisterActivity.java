package com.example.firebase_chat_basic.view.activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.ActivityRegisterBinding;
import com.example.firebase_chat_basic.viewModel.RegisterViewModel;


public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding activityRegisterBinding;
    private RegisterViewModel registerViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        activityRegisterBinding.setRegisterViewModel(registerViewModel);

        observerIntent();
    }

    // The method is intentMethod for MainActivity.class
    // Get data from "datList" in the "RegisterViewModel"
    // Then, this method is deliver to "MainActivity"

    public void observerIntent(){
        registerViewModel.getDataList.observe(this, registerData -> {
            Intent registerIntent = new Intent(this, MainActivity.class);
            registerIntent.putExtra("clientName", registerData.get(0));
            registerIntent.putExtra("clientEmail", registerData.get(1));
            registerIntent.putExtra("clientPassword", registerData.get(2));
            startActivity(registerIntent);
            finish();

            System.out.println(registerData.get(0).toString());
            System.out.println(registerData.get(1).toString());
            System.out.println(registerData.get(2).toString());

            System.out.println("전부 잘 들어갔어요");
        });

    }
}
