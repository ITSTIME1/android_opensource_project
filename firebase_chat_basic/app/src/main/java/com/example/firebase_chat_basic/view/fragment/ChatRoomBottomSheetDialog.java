package com.example.firebase_chat_basic.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.ActivityChatroomUploadBottomDialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;


/**
 * [ChatRoomBottomSheetDialog] - StickCode
 */
public class ChatRoomBottomSheetDialog extends BottomSheetDialogFragment{
    private ActivityChatroomUploadBottomDialogBinding activityChatroomUploadBottomDialogBinding;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private ImageView imageView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activityChatroomUploadBottomDialogBinding = DataBindingUtil.inflate(inflater, R.layout.activity_chatroom_upload_bottom_dialog, container, false);
        picture_access();
        picture_access_result();
        glide();
        return activityChatroomUploadBottomDialogBinding.getRoot();
    }

    // glide bottomSheetDialog image
    public void glide(){
        ImageView bottomSheetDialog_gallery = activityChatroomUploadBottomDialogBinding.chatroomActivityGridMyPicture;
        GlideDrawableImageViewTarget bottomSheet_Picture= new GlideDrawableImageViewTarget(bottomSheetDialog_gallery);
        Glide.with(getContext()).load(R.raw.galleryicon).into(bottomSheet_Picture);

        ImageView bottomSheetDialog_video = activityChatroomUploadBottomDialogBinding.chatroomActivityGridMyVideo;
        GlideDrawableImageViewTarget bottomSheet_Video= new GlideDrawableImageViewTarget(bottomSheetDialog_video);
        Glide.with(getContext()).load(R.raw.videoicon).into(bottomSheet_Video);

        ImageView bottomSheetDialog_reservation = activityChatroomUploadBottomDialogBinding.chatroomActivityGridMyReservationMessage;
        GlideDrawableImageViewTarget bottomSheet_Reservation= new GlideDrawableImageViewTarget(bottomSheetDialog_reservation);
        Glide.with(getContext()).load(R.raw.messageicon).into(bottomSheet_Reservation);

        ImageView bottomSheetDialog_Voice = activityChatroomUploadBottomDialogBinding.chatroomActivityGridRecordVoice;
        GlideDrawableImageViewTarget bottomSheet_Voice= new GlideDrawableImageViewTarget(bottomSheetDialog_Voice);
        Glide.with(getContext()).load(R.raw.voiceicon).into(bottomSheet_Voice);

        ImageView bottomSheetDialog_Call = activityChatroomUploadBottomDialogBinding.chatroomActivityGridCall;
        GlideDrawableImageViewTarget bottomSheet_Call= new GlideDrawableImageViewTarget(bottomSheetDialog_Call);
        Glide.with(getContext()).load(R.raw.callicon).into(bottomSheet_Call);

        ImageView bottomSheetDialog_Camera = activityChatroomUploadBottomDialogBinding.chatroomActivityGridCamera;
        GlideDrawableImageViewTarget bottomSheet_Camera = new GlideDrawableImageViewTarget(bottomSheetDialog_Camera);
        Glide.with(getContext()).load(R.raw.cameraicon).into(bottomSheet_Camera);
    }


    // picture access method
    public void picture_access(){
        activityChatroomUploadBottomDialogBinding.chatroomActivityGridMyPicture.setOnClickListener(view -> {
            if(view != null) {
                Intent bottomSheetIntent = new Intent(Intent.ACTION_PICK);
                bottomSheetIntent.setType("image/*");
                activityResultLauncher.launch(bottomSheetIntent);
            }
        });
    }

    // get image result code
    public void picture_access_result(){
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        // doSomeOperations();
                        Intent data = result.getData();
                        Uri selectedImage = Objects.requireNonNull(data).getData();
                        InputStream imageStream = null;
                        try {
                            imageStream = requireContext().getContentResolver().openInputStream(selectedImage);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        BitmapFactory.decodeStream(imageStream);
                        imageView.setImageURI(selectedImage);// To display selected image in image view
                    }
                });
    }



    // video access method
    public void video_access(){}
    // reservation message method
    public void reservation_message(){}
    // voice record method
    public void voice_record(){}
    // call method
    public void call(){}
    // camera method
    public void camera(){}
}
