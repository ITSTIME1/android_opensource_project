package model;


public class HomeVerModel {
    int verImage;
    String verFoodName;
    String verFoodDescription;

    public HomeVerModel(int verImage, String verFoodName, String verFoodDescription) {
        this.verImage = verImage;
        this.verFoodName = verFoodName;
        this.verFoodDescription = verFoodDescription;
    }

    public int getVerImage() {
        return verImage;
    }

    public void setVerImage(int verImage) {
        this.verImage = verImage;
    }

    public String getVerFoodName() {
        return verFoodName;
    }

    public void setVerFoodName(String verFoodName) {
        this.verFoodName = verFoodName;
    }

    public String getVerFoodDescription() {
        return verFoodDescription;
    }

    public void setVerFoodDescription(String verFoodDescription) {
        this.verFoodDescription = verFoodDescription;
    }
}
