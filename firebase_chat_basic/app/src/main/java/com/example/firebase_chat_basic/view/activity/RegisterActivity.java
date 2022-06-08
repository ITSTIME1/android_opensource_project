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
import com.example.firebase_chat_basic.view.fragment.ChatFragment;
import com.example.firebase_chat_basic.viewModel.RegisterViewModel;

/**
 * [RegisterActivity]
 *
 * 1.
 * The class used to "DataBinding" and "MVVM"
 * If client want to "register" then move here.
 *
 * 2.
 * and then receives data written by the client and get data from "RegisterViewModel"
 * Finally, it is handled here.
 */
public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding activityRegisterBinding;
    private RegisterViewModel registerViewModel;
    private ChatFragment chatFragment;
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

            // Activity -> Activity
            Intent registerIntent = new Intent(this, MainActivity.class);
            registerIntent.putExtra("clientName", registerData.get(0));
            registerIntent.putExtra("clientEmail", registerData.get(1));
            registerIntent.putExtra("clientPassword", registerData.get(2));
            registerIntent.putExtra("clientUUID", registerData.get(3));
            registerIntent.putExtra("clientProfileImage", registerData.get(4));

            if(registerData.get(0) == null && registerData.get(1) == null && registerData.get(2) == null) {
                System.out.println("Null");
                System.out.println("강제종료");
            } else {
                startActivity(registerIntent);
                finish();
            }

            System.out.println("=============================");
            System.out.println("RegisterActivity - succeeded");
            System.out.println(registerData.get(0).toString());
            System.out.println(registerData.get(1).toString());
            System.out.println(registerData.get(2).toString());
            System.out.println(registerData.get(3).toString());
            System.out.println(registerData.get(4).toString());
            System.out.println("=============================");
        });

    }
}
