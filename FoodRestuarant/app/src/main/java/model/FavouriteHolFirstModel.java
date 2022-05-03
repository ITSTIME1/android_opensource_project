package model;

public class FavouriteHolFirstModel {
    int favFirstHolImage;
    String favFirstHolName;
    String favFirstHolDescription;

    public FavouriteHolFirstModel(int favFirstHolImage, String favFirstHolName, String favFirstHolDescription) {
        this.favFirstHolImage = favFirstHolImage;
        this.favFirstHolName = favFirstHolName;
        this.favFirstHolDescription = favFirstHolDescription;
    }

    public int getFavFirstHolImage() {
        return favFirstHolImage;
    }

    public void setFavFirstHolImage(int favFirstHolImage) {
        this.favFirstHolImage = favFirstHolImage;
    }

    public String getFavFirstHolName() {
        return favFirstHolName;
    }

    public void setFavFirstHolName(String favFirstHolName) {
        this.favFirstHolName = favFirstHolName;
    }

    public String getFavFirstHolDescription() {
        return favFirstHolDescription;
    }

    public void setFavFirstHolDescription(String favFirstHolDescription) {
        this.favFirstHolDescription = favFirstHolDescription;
    }
}
