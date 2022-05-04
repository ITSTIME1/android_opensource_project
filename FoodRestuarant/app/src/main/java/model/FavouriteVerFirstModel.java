package model;

public class FavouriteVerFirstModel {
    public int favFirstVerImage;
    public String favFirstVerName;
    public String favFirstVerDescription;

    public FavouriteVerFirstModel(int favFirstVerImage, String favFirstVerName, String favFirstVerDescription) {
        this.favFirstVerImage = favFirstVerImage;
        this.favFirstVerName = favFirstVerName;
        this.favFirstVerDescription = favFirstVerDescription;
    }

    public int getFavFirstVerImage() {
        return favFirstVerImage;
    }

    public void setFavFirstVerImage(int favFirstVerImage) {
        this.favFirstVerImage = favFirstVerImage;
    }

    public String getFavFirstVerName() {
        return favFirstVerName;
    }

    public void setFavFirstVerName(String favFirstVerName) {
        this.favFirstVerName = favFirstVerName;
    }

    public String getFavFirstVerDescription() {
        return favFirstVerDescription;
    }

    public void setFavFirstVerDescription(String favFirstVerDescription) {
        this.favFirstVerDescription = favFirstVerDescription;
    }
}
