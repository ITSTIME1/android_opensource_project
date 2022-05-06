package model;

public class BottomSheetModel {
    public int bottomSheetImage;
    public String bottomSheetLogoName;
    public String bottomSheetTitleName;
    public String bottomSheetDescriptionName;

    public BottomSheetModel(int bottomSheetImage, String bottomSheetLogoName, String bottomSheetTitleName, String bottomSheetDescriptionName) {
        this.bottomSheetImage = bottomSheetImage;
        this.bottomSheetLogoName = bottomSheetLogoName;
        this.bottomSheetTitleName = bottomSheetTitleName;
        this.bottomSheetDescriptionName = bottomSheetDescriptionName;
    }

    public int getBottomSheetImage() {
        return bottomSheetImage;
    }

    public void setBottomSheetImage(int bottomSheetImage) {
        this.bottomSheetImage = bottomSheetImage;
    }

    public String getBottomSheetLogoName() {
        return bottomSheetLogoName;
    }

    public void setBottomSheetLogoName(String bottomSheetLogoName) {
        this.bottomSheetLogoName = bottomSheetLogoName;
    }

    public String getBottomSheetTitleName() {
        return bottomSheetTitleName;
    }

    public void setBottomSheetTitleName(String bottomSheetTitleName) {
        this.bottomSheetTitleName = bottomSheetTitleName;
    }

    public String getBottomSheetDescriptionName() {
        return bottomSheetDescriptionName;
    }

    public void setBottomSheetDescriptionName(String bottomSheetDescriptionName) {
        this.bottomSheetDescriptionName = bottomSheetDescriptionName;
    }
}
