package com.example.firebase_chat_basic.view.activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.ActivityPictureBinding;



/**
 * [PictureActivity]
 *
 * Your selected image showed in pictureSelectedImage
 */
public class PictureActivity extends AppCompatActivity {
    private ActivityPictureBinding activityPictureBinding;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPictureBinding = DataBindingUtil.setContentView(this, R.layout.activity_picture);

        Intent imageIntent = getIntent();
        Uri uri = imageIntent.getParcelableExtra("select_image");
        activityPictureBinding.pictureSelectImage.setImageURI(uri);
    }
}
