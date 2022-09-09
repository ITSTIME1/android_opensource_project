package com.example.firebase_chat_basic.view.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.firebase_chat_basic.Interface.BaseInterface;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.ActivityChatroomUploadBottomDialogBinding;
import com.example.firebase_chat_basic.view.activity.CameraXActivity;
import com.example.firebase_chat_basic.view.activity.ChatRoomActivity;
import com.example.firebase_chat_basic.view.activity.PictureActivity;
import com.example.firebase_chat_basic.view.activity.VideoActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.Serializable;
import java.util.List;

import gun0912.tedimagepicker.builder.TedImagePicker;
import gun0912.tedimagepicker.builder.listener.OnMultiSelectedListener;
import gun0912.tedimagepicker.builder.listener.OnSelectedListener;


/**
 * [ChatRoomBottomSheetDialog] - StickCode
 */


// @TODO imagePicker 완성 시키기
public class ChatRoomBottomSheetDialog extends BottomSheetDialogFragment implements BaseInterface {
    private ActivityChatroomUploadBottomDialogBinding activityChatroomUploadBottomDialogBinding;
    private String getChatKey;
    private String getOtherUID;
    private String getMyUID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activityChatroomUploadBottomDialogBinding = DataBindingUtil.inflate(inflater, R.layout.activity_chatroom_upload_bottom_dialog, container, false);
        initialize();
        getDataIntent();
        glide();
        return activityChatroomUploadBottomDialogBinding.getRoot();
    }

    @Override
    public void getDataIntent() {
        BaseInterface.super.getDataIntent();
        // get data from chatRoom Activity
        Bundle getArgument = getArguments();
        if(getArgument != null) {
            getChatKey = getArgument.getString("getChatPrivateKey");
            getOtherUID = getArgument.getString("getOtherUID");
            getMyUID = getArgument.getString("getMyUID");
        }
    }

    @Override
    public void initialize() {
        BaseInterface.super.initialize();
        activityChatroomUploadBottomDialogBinding.setChatRoomBottomSheetDialog(this);
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


    // picture access method (Databinding)
    public void pictureAccess() {
        permissionCheckForImage();
        // TedImagePicker - image selected
        TedImagePicker.with(requireContext()).startMultiImage(new OnMultiSelectedListener() {

            @Override
            public void onSelected(@NonNull List<? extends Uri> list) {
                Intent multiImageIntent = new Intent(getContext(), PictureActivity.class);
                multiImageIntent.putExtra("selectedImage", (Serializable) list);
                multiImageIntent.putExtra("getChatPrivateKey", getChatKey);
                multiImageIntent.putExtra("getOtherUID", getOtherUID);
                multiImageIntent.putExtra("getMyUID", getMyUID);
                Log.d("selectedImage", String.valueOf(list));
                startActivity(multiImageIntent);
                dismiss();
//                        Log.d("성Ima공적으로 intent list 전달 성공 ", String.valueOf(multiImageIntent));
            }
        });
    }

    // ted permission check
    public void permissionCheckForImage() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(getContext(), "권한이 허가된 상태입니다", Toast.LENGTH_SHORT).show();
                Log.e("권한", "권한 허가 상태");
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
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

    public void videoAccess() {
        TedImagePicker.with(requireContext()).video().start(new OnSelectedListener() {
            @Override
            public void onSelected(@NonNull Uri uri) {
                Intent videoIntent = new Intent(getContext(), VideoActivity.class);
                videoIntent.putExtra("videoIntent", uri);
                videoIntent.putExtra("getChatPrivateKey", getChatKey);
                videoIntent.putExtra("getOtherUID", getOtherUID);
                videoIntent.putExtra("getMyUID", getMyUID);
                Log.d("uri 체크좀 해볼게요 videoAccess ", String.valueOf(uri));
                startActivity(videoIntent);
                dismiss();
            }
        });
    }

    // reservation message method
    private void reservation_message() {
        dismiss();
    }

    // voice record method
    private void voice_record() {
        dismiss();
    }

    // callToUser method
    public void callToUser() {
        ChatRoomActivity chatRoomActivity = new ChatRoomActivity();
        final String call_number = chatRoomActivity.getGetPhoneNumber();
        Log.d("call_number", String.valueOf(call_number));
        Intent bottomCallIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "0" + call_number));
        try {
            startActivity(bottomCallIntent);
            dismiss();
            Log.d("callToUser intent 가 작동 되나요", "");
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    // callback camera method
    public void camera(){
        Intent cameraIntent = new Intent(getActivity(), CameraXActivity.class);
        cameraIntent.putExtra("getChatPrivateKey", getChatKey);
        cameraIntent.putExtra("getOtherUID", getOtherUID);
        cameraIntent.putExtra("getMyUID", getMyUID);
        startActivity(cameraIntent);
        dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activityChatroomUploadBottomDialogBinding = null;
    }
}
