package com.example.firebase_chat_basic.model;


/**
 * [ImageViewerModel]
 *
 * <Topic>
 *     This model is for "imageViewer model" only show image
 * </Topic>
 *
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
