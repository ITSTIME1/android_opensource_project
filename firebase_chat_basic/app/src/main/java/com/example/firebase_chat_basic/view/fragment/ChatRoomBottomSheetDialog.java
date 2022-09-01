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
    private String get_chat_key;
    private String get_other_uid;
    private String get_current_my_uid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activityChatroomUploadBottomDialogBinding = DataBindingUtil.inflate(inflater, R.layout.activity_chatroom_upload_bottom_dialog, container, false);
        default_init();
        get_data_intent();
        glide();
        return activityChatroomUploadBottomDialogBinding.getRoot();
    }

    @Override
    public void get_data_intent() {
        BaseInterface.super.get_data_intent();
        // get data from chatRoom Activity
        Bundle getArgument = getArguments();
        if(getArgument != null) {
            get_chat_key = getArgument.getString("get_chat_key");
            get_other_uid = getArgument.getString("get_other_uid");
            get_current_my_uid = getArgument.getString("get_current_my_uid");
        }
    }

    @Override
    public void default_init() {
        BaseInterface.super.default_init();
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
    public void picture_access() {
        permission_image();
        // TedImagePicker - image selected
        TedImagePicker.with(requireContext()).startMultiImage(new OnMultiSelectedListener() {

            @Override
            public void onSelected(@NonNull List<? extends Uri> list) {
                Intent multiImageIntent = new Intent(getContext(), PictureActivity.class);
                multiImageIntent.putExtra("selectedImage", (Serializable) list);
                multiImageIntent.putExtra("get_chat_key", get_chat_key);
                multiImageIntent.putExtra("get_other_uid", get_other_uid);
                multiImageIntent.putExtra("get_current_my_uid", get_current_my_uid);
                Log.d("selectedImage", String.valueOf(list));
                startActivity(multiImageIntent);
                dismiss();
//                        Log.d("성Ima공적으로 intent list 전달 성공 ", String.valueOf(multiImageIntent));
            }
        });
    }

    // ted permission check
    public void permission_image() {
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

    public void video_access() {
        TedImagePicker.with(requireContext()).video().start(new OnSelectedListener() {
            @Override
            public void onSelected(@NonNull Uri uri) {
                Intent videoIntent = new Intent(getContext(), VideoActivity.class);
                videoIntent.putExtra("videoIntent", uri);
                videoIntent.putExtra("get_chat_key", get_chat_key);
                videoIntent.putExtra("get_other_uid", get_other_uid);
                videoIntent.putExtra("get_current_my_uid", get_current_my_uid);
                Log.d("uri 체크좀 해볼게요 video_access ", String.valueOf(uri));
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

    // call method
    public void call() {
        ChatRoomActivity chatRoomActivity = new ChatRoomActivity();
        final String call_number = chatRoomActivity.getGet_phone_number();
        Log.d("call_number", String.valueOf(call_number));
        Intent bottomCallIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "0" + call_number));
        try {
            startActivity(bottomCallIntent);
            dismiss();
            Log.d("call intent 가 작동 되나요", "");
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    // callback camera method
    public void camera(){
        Intent cameraIntent = new Intent(getActivity(), CameraXActivity.class);
        cameraIntent.putExtra("get_chat_key", get_chat_key);
        cameraIntent.putExtra("get_other_uid", get_other_uid);
        cameraIntent.putExtra("get_current_my_uid", get_current_my_uid);
        startActivity(cameraIntent);
        dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activityChatroomUploadBottomDialogBinding = null;
    }
}
