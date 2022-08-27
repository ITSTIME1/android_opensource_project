package com.example.firebase_chat_basic.model;

import android.net.Uri;

public class AsyncImageModel {
    private Uri async_image_url;

    public AsyncImageModel(Uri async_image_url) {
        this.async_image_url = async_image_url;
    }

    public Uri getAsync_image_url() {
        return async_image_url;
    }

    public void setAsync_image_url(Uri async_image_url) {
        this.async_image_url = async_image_url;
    }
}
