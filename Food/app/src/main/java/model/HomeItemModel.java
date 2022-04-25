package model;


// * HomeItem Model


public class HomeItemModel {

    int image;
    String name;

    // HomeItem Constructor
    public HomeItemModel(int image, String name) {
        this.image = image;
        this.name = name;
    }

    // Import Image Method
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    // Import Name Method
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
