package com.example.firebase_chat_basic.model;


/**
 * [ImageViewerModel]
 *
 * This object is "imageViewer model" only show image
 */
public class ImageViewerModel {
    private String image_viewer;

    public ImageViewerModel(String image_viewer) {
        this.image_viewer = image_viewer;
    }

    public String getImage_viewer() {
        return image_viewer;
    }

    public void setImage_viewer(String image_viewer) {
        this.image_viewer = image_viewer;
    }
}
