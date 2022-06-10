package com.example.firebase_chat_basic.view.activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

            Intent registerIntent = new Intent(this, MainActivity.class);

            if(registerData.get(0) != null && registerData.get(1) != null && registerData.get(2) != null) {
                registerIntent.putExtra("clientUID", registerData.get(0));
                registerIntent.putExtra("clientName", registerData.get(1));
                registerIntent.putExtra("clientEmail", registerData.get(2));
                registerIntent.putExtra("clientProfileImage", registerData.get(3));
                Log.d("registerIntent", "success");
            } else {
                Log.d("registerData", "register Data null");
            }
            startActivity(registerIntent);
            finish();
        });
    }
}
