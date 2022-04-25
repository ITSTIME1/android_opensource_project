package model;

import adapters.HomeVerticalItemAdapter;

public class HomeVerticalItemModel {


    // Member variables
    int image;
    String vertical_item_name;
    String vertical_item_description;



    // Vertical_item_constructor
    public HomeVerticalItemModel(int image, String vertical_item_name, String vertical_item_description) {
        this.image = image;
        this.vertical_item_name = vertical_item_name;
        this.vertical_item_description = vertical_item_description;
    }


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getVertical_item_name() {
        return vertical_item_name;
    }

    public void setVertical_item_name(String vertical_item_name) {
        this.vertical_item_name = vertical_item_name;
    }

    public String getVertical_item_description() {
        return vertical_item_description;
    }

    public void setVertical_item_description(String vertical_item_description) {
        this.vertical_item_description = vertical_item_description;
    }
}
