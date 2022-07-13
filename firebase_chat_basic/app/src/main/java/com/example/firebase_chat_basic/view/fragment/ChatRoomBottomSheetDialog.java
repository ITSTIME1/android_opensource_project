package com.example.firebase_chat_basic.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.ActivityChatroomUploadBottomDialogBinding;
import com.example.firebase_chat_basic.view.activity.PictureActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.annotations.NotNull;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import gun0912.tedimagepicker.builder.TedImagePicker;
import gun0912.tedimagepicker.builder.listener.OnMultiSelectedListener;
import gun0912.tedimagepicker.builder.listener.OnSelectedListener;


/**
 * [ChatRoomBottomSheetDialog] - StickCode
 */
public class ChatRoomBottomSheetDialog extends BottomSheetDialogFragment{
    private ActivityChatroomUploadBottomDialogBinding activityChatroomUploadBottomDialogBinding;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    public Bitmap callback_image;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activityChatroomUploadBottomDialogBinding = DataBindingUtil.inflate(inflater, R.layout.activity_chatroom_upload_bottom_dialog, container, false);
        picture_access();
//        picture_access_result();
        glide();
        return activityChatroomUploadBottomDialogBinding.getRoot();
    }

    // glide bottomSheetDialog image
    private void glide() {
        Glide.with(requireContext())
                .load(R.raw.galleryicon)
                .into(activityChatroomUploadBottomDialogBinding.chatroomActivityGridMyPicture);

        Glide.with(requireContext())
                .load(R.raw.videoicon)
                .into(activityChatroomUploadBottomDialogBinding.chatroomActivityGridMyVideo);

        Glide.with(requireContext())
                .load(R.raw.messageicon)
                .into(activityChatroomUploadBottomDialogBinding.chatroomActivityGridMyReservationMessage);

        Glide.with(requireContext())
                .load(R.raw.voiceicon)
                .into(activityChatroomUploadBottomDialogBinding.chatroomActivityGridRecordVoice);

        Glide.with(requireContext())
                .load(R.raw.callicon)
                .into(activityChatroomUploadBottomDialogBinding.chatroomActivityGridCall);

        Glide.with(requireContext())
                .load(R.raw.cameraicon)
                .into(activityChatroomUploadBottomDialogBinding.chatroomActivityGridCamera);
    }


    // picture access method
    public void picture_access(){
        activityChatroomUploadBottomDialogBinding.chatroomActivityGridMyPicture.setOnClickListener(view -> {
            if(view != null) {
                // ted-permission check
                permission_image();
//                Intent bottomSheetIntent = new Intent(Intent.ACTION_PICK);
//                bottomSheetIntent.setType("image/*");
//                activityResultLauncher.launch(bottomSheetIntent);


                // TedImagePicker - image selected
                TedImagePicker.with(requireContext())
                        .start(new OnSelectedListener() {
                            @Override
                            public void onSelected(@NonNull Uri uri) {
                                Log.d("선택되었습니다.", "");
                            }
                        });
            }
        });
    }

    // ted permission check
    public void permission_image(){
        PermissionListener permissionListener = new PermissionListener()
        {
            @Override
            public void onPermissionGranted()
            {
                Toast.makeText(getContext(), "권한이 허가된 상태입니다", Toast.LENGTH_SHORT).show();
                Log.e("권한", "권한 허가 상태");
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions)
            {
                Toast.makeText(getContext(), "권한이 거부된 상태입니다", Toast.LENGTH_SHORT).show();
                Log.e("권한", "권한 거부 상태");
            }
        };
        TedPermission.create()
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

//    // callback method
//    public void picture_access_result() {
//        activityResultLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        // There are no request codes
//                        // doSomeOperations();
//                        Intent data = result.getData();
//                        Uri selectedImage = Objects.requireNonNull(data).getData();
//                        InputStream imageStream = null;
//                        try {
//                            imageStream = requireContext().getContentResolver().openInputStream(selectedImage);
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        }
//                        // 1. 이미지를 PictureActivity로 보내준다.
//                        // 2. uri 그대로 보내주고 pictureActivity 에서 사진 편집, 색감 조정, 등등 기능을 수행한 뒤 채팅을 보낸다.
//                        BitmapFactory.decodeStream(imageStream);
//
//                        Intent imageIntent = new Intent(getActivity(), PictureActivity.class);
//                        imageIntent.putExtra("select_image", selectedImage);
//                        startActivity(imageIntent);
//                        Log.d("이미지 데이터 확인 ", String.valueOf(imageIntent));
//                    }
//                });
//    }



    // video access method
    private void video_access() {
    }

    // reservation message method
    private void reservation_message() {
    }

    // voice record method
    private void voice_record() {
    }

    // call method
    private void call() {
    }

    // camera method
    private void camera() {
    }
}
