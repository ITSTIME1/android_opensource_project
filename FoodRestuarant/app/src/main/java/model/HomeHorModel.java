package model;

public class HomeHorModel {
    int image;
    String imgName;

    public HomeHorModel(int image, String imgName) {
        this.image = image;
        this.imgName = imgName;
    }


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
}
