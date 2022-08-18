package com.example.firebase_chat_basic.view.fragment;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.ActivityChatRoomImageBottomDialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

/**
 * [ChatRoomImageBottomSheetDialog]
 *
 * <Topic>
 *     This fragment is for "image"
 * </Topic>
 */
public class ChatRoomImageBottomSheetDialog extends BottomSheetDialogFragment {
    private ActivityChatRoomImageBottomDialogBinding activityChatRoomImageBottomDialogBinding;
    private String getImageURL;

    @SuppressLint("UseRequireInsteadOfGet")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activityChatRoomImageBottomDialogBinding =  DataBindingUtil.inflate(inflater, R.layout.activity_chat_room_image_bottom_dialog, container, false);
        Bundle getBundle = getArguments();
        if (getBundle != null) {
            getImageURL = getBundle.getString("chatRoomImageURL");
            Log.d("getImageURL", getImageURL);
        }
        Glide.with(requireActivity()).load(getImageURL).into(activityChatRoomImageBottomDialogBinding.chatRoomImageBottomDialog);

        return activityChatRoomImageBottomDialogBinding.getRoot();
    }
}
