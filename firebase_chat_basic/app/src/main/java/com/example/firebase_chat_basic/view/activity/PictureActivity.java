package com.example.firebase_chat_basic.view.activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.example.firebase_chat_basic.Interface.BaseInterface;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.adapters.ImageViewerAdapter;
import com.example.firebase_chat_basic.databinding.ActivityPictureBinding;
import com.example.firebase_chat_basic.model.ImageViewerModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * [PictureActivity]
 *
 * MultiSelectImageViewer Create
 */


public class PictureActivity extends AppCompatActivity implements BaseInterface {
    private ImageViewerAdapter imageViewerAdapter;
    private ActivityPictureBinding activityPictureBinding;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPictureBinding = DataBindingUtil.setContentView(this, R.layout.activity_picture);
        default_init();
        get_data_intent();

    }

    @Override
    public void default_init() {
        BaseInterface.super.default_init();
        activityPictureBinding.setPictureActivity(this);
        activityPictureBinding.setLifecycleOwner(this);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void get_data_intent() {
        BaseInterface.super.get_data_intent();
        Intent getIntent = getIntent();
        // 이미지 까지는 잘 들어오는데 imageView 에 적용이 안됨.
        //2022-07-17 19:39:48.894 10264-10264/com.example.firebase_chat_basic D/getSelectedList: 2
        //2022-07-17 19:39:48.894 10264-10264/com.example.firebase_chat_basic D/imageViewerList value: content://media/external/images/media/41
        //2022-07-17 19:39:48.894 10264-10264/com.example.firebase_chat_basic D/imageViewerList value: content://media/external/images/media/57
        //2022-07-17 19:39:48.894 10264-10264/com.example.firebase_chat_basic D/imageViewerModelArrayList 개수: 2
        ArrayList<ImageViewerModel> imageViewerModelArrayList = new ArrayList<>();
        List<Object> getSelectedList = Collections.singletonList(getIntent.getSerializableExtra("selectedImage"));

        if(getSelectedList != null) {
            for (int i = 0; i < getSelectedList.size(); i++) {
                imageViewerModelArrayList.add(new ImageViewerModel(getSelectedList.get(i).toString()));
                Log.d("imageViewerList value", imageViewerModelArrayList.get(i).getImage_viewer());
            }
            imageViewerAdapter = new ImageViewerAdapter(imageViewerModelArrayList);
            imageViewerAdapter.notifyDataSetChanged();
        }
    }

    // backPressed method
    public void onBackPressed(){
        finish();
    }

    // binding imageViewerAdapter
    public ImageViewerAdapter getImageViewerAdapter(){
        return imageViewerAdapter;
    }

}
