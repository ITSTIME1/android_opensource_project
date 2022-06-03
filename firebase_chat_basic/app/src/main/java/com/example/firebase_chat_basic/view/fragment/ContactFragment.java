package com.example.firebase_chat_basic.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.FragmentContactBinding;

public class ContactFragment extends Fragment {
    private FragmentContactBinding fragmentContactBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentContactBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false);

        /*
         *  Do this
         */

        return fragmentContactBinding.getRoot();
    }
}
