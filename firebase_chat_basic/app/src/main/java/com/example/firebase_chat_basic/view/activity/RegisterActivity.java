package com.example.firebase_chat_basic.view.activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.firebase_chat_basic.Interface.BaseInterface;
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
public class RegisterActivity extends AppCompatActivity implements BaseInterface {
    private ActivityRegisterBinding activityRegisterBinding;
    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        defaultInit();
        observerIntent();
    }

    @Override
    public void defaultInit() {
        BaseInterface.super.defaultInit();
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        activityRegisterBinding.setRegisterViewModel(registerViewModel);
    }

    // The method is intentMethod for MainActivity.class
    // Get data from "datList" in the "RegisterViewModel"
    // Then, this method is deliver to "MainActivity"

    @Override
    public void observerIntent() {
        BaseInterface.super.observerIntent();
        registerViewModel.getDataList.observe(this, registerData -> {

            // Activity -> Activity
            // 이름, 이메일, uid, image 전송.
            Intent registerIntent = new Intent(this, MainActivity.class);
            registerIntent.putExtra("clientName", registerData.get(0));
            registerIntent.putExtra("clientEmail", registerData.get(1));
            registerIntent.putExtra("clientUUID", registerData.get(3));
            registerIntent.putExtra("clientProfileImage", registerData.get(4));

            if(registerData.get(0) != null && registerData.get(1) != null && registerData.get(3) != null && registerData.get(4) != null) {
                startActivity(registerIntent);
                finish();
            } else {
                System.out.println("registerData 값이 null이 있습니다.");
            }

            System.out.println("=============================");
            System.out.println("RegisterActivity - succeeded");
            System.out.println(registerData.get(0).toString());
            System.out.println(registerData.get(1).toString());
            System.out.println(registerData.get(3).toString());
            System.out.println(registerData.get(4).toString());
            System.out.println("=============================");
        });
    }
}
