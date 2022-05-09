package Models;

// ** HomeFragment HorRec1 ItemModel **
// This model is first horizontal fragment model


public class FragmentHomeHolRecItemModel {
    private int frgHomeHolRecImage;
    private String fragHomeHolRecTitle;
    private String fragHomeHolRecDescription;
    private String fragHomeHolRecPrice;

    public String getFragHomeHolRecOrderText() {
        return fragHomeHolRecOrderText;
    }

    public void setFragHomeHolRecOrderText(String fragHomeHolRecOrderText) {
        this.fragHomeHolRecOrderText = fragHomeHolRecOrderText;
    }

    private String fragHomeHolRecOrderText;

    public FragmentHomeHolRecItemModel(int frgHomeHolRecImage, String fragHomeHolRecTitle, String fragHomeHolRecDescription, String fragHomeHolRecPrice, String fragHomeHolRecOrderText) {
        this.frgHomeHolRecImage = frgHomeHolRecImage;
        this.fragHomeHolRecTitle = fragHomeHolRecTitle;
        this.fragHomeHolRecDescription = fragHomeHolRecDescription;
        this.fragHomeHolRecPrice = fragHomeHolRecPrice;
        this.fragHomeHolRecOrderText = fragHomeHolRecOrderText;
    }

    public int getFrgHomeHolRecImage() {
        return frgHomeHolRecImage;
    }

    public void setFrgHomeHolRecImage(int frgHomeHolRecImage) {
        this.frgHomeHolRecImage = frgHomeHolRecImage;
    }

    public String getFragHomeHolRecTitle() {
        return fragHomeHolRecTitle;
    }

    public void setFragHomeHolRecTitle(String fragHomeHolRecTitle) {
        this.fragHomeHolRecTitle = fragHomeHolRecTitle;
    }

    public String getFragHomeHolRecDescription() {
        return fragHomeHolRecDescription;
    }

    public void setFragHomeHolRecDescription(String fragHomeHolRecDescription) {
        this.fragHomeHolRecDescription = fragHomeHolRecDescription;
    }

    public String getFragHomeHolRecPrice() {
        return fragHomeHolRecPrice;
    }

    public void setFragHomeHolRecPrice(String fragHomeHolRecPrice) {
        this.fragHomeHolRecPrice = fragHomeHolRecPrice;
    }
}
