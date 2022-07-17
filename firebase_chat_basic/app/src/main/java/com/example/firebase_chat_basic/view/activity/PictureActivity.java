package com.example.firebase_chat_basic.view.activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
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
import java.util.LinkedList;
import java.util.List;


/**
 * [PictureActivity]
 *
 * MultiSelectImageViewer Create
 */


public class PictureActivity extends AppCompatActivity implements BaseInterface {
    private ArrayList<ImageViewerModel> imageViewerModelList;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.firebase_chat_basic.databinding.ActivityPictureBinding
                activityPictureBinding = DataBindingUtil.setContentView(this, R.layout.activity_picture);
        get_data_intent();

    }

    @Override
    public void default_init() {
        BaseInterface.super.default_init();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void get_data_intent() {
        BaseInterface.super.get_data_intent();
        Intent getIntent = getIntent();
        // 이미지 값은 잘 들어온다
        // @TODO 이미지의 경로를 content: 스트링 값을 지우고 저장해야 경로를 저장한다.


        //D/urilist 확인: [content://media/external/images/media/57, content://media/external/images/media/55]
        //2022-07-16 00:05:47.643 23207-23207/com.example.firebase_chat_basic D/uriList: [content://media/external/images/media/57, content://media/external/images/media/55]
        // "/572/내그림/image_sample01.jpg"
        //2022-07-16 00:05:47.643 23207-23207/com.example.firebase_chat_basic D/uriList: [content://media/external/images/media/57, content://media/external/images/media/55]


        // multiImage 의 content://media/external/images/media/57 = content 없이 //media/external/images/media/57
        // 오직 이미지의 경로만을 활용해서 이미지를 리사이클러뷰에 저장한다.

        // split_string: [content, //media/external/images/media/41]
        // D/split_string: [content, //media/external/images/media/40]
        List<Uri> uriList = getIntent.getParcelableArrayListExtra("multiImage");
        for (int i = 0; i<uriList.size(); i++) {
            // 이게 지금 리시트가 아니라 string 이다.
            String subString = uriList.get(i).toString();
            String media_root = subString.substring(8);

            if(imageViewerModelList == null) {
                imageViewerModelList = new ArrayList<>();
            }

            imageViewerModelList.add(new ImageViewerModel(media_root));
            ImageViewerAdapter imageViewerAdapter = new ImageViewerAdapter(imageViewerModelList, PictureActivity.this);
            Log.d("modelList ", imageViewerModelList.get(i).getImage_viewer());
            // subString 함수를 통해 split 된 media_root 만 전달
            imageViewerAdapter.notifyDataSetChanged();
        }
    }

}
