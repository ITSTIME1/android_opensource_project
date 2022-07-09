package com.example.firebase_chat_basic.view.activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.firebase_chat_basic.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ChatRoomBottomSheetDialog extends BottomSheetDialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chatroom_upload_bottom_dialog, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
