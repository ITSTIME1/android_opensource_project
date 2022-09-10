package com.example.firebase_chat_basic.view.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.FragmentCustomImageBinding;

import java.util.Objects;

/**
 * [CustomImageDialogFragment]
 *
 * This is for image of chatMessageRecyclerview.
 * When user click itemImage, show the own imageURL.
 */


public class CustomImageDialogFragment extends DialogFragment {
    private FragmentCustomImageBinding fragmentCustomImageBinding;
    private final String imageURL;

    // constructor
    public CustomImageDialogFragment(String url) {
        this.imageURL = url;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        fragmentCustomImageBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_custom_image, null, false);
        fragmentCustomImageBinding.setCustomImage(this);

        // image glide
        Glide.with(requireActivity()).load(imageURL).into(fragmentCustomImageBinding.customImageView);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(fragmentCustomImageBinding.getRoot());

        return builder.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentCustomImageBinding = null;
    }
}
