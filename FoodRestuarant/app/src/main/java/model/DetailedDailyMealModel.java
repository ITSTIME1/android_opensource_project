package model;

public class DetailedDailyMealModel {
    int detailedDailyMealImage;
    String detailedDailyMealName;
    String detailedDailyMealDescription;

    public DetailedDailyMealModel(int detailedDailyMealImage, String detailedDailyMealName, String detailedDailyMealDescription) {
        this.detailedDailyMealImage = detailedDailyMealImage;
        this.detailedDailyMealName = detailedDailyMealName;
        this.detailedDailyMealDescription = detailedDailyMealDescription;
    }

    public int getDetailedDailyMealImage() {
        return detailedDailyMealImage;
    }

    public void setDetailedDailyMealImage(int detailedDailyMealImage) {
        this.detailedDailyMealImage = detailedDailyMealImage;
    }

    public String getDetailedDailyMealName() {
        return detailedDailyMealName;
    }

    public void setDetailedDailyMealName(String detailedDailyMealName) {
        this.detailedDailyMealName = detailedDailyMealName;
    }

    public String getDetailedDailyMealDescription() {
        return detailedDailyMealDescription;
    }

    public void setDetailedDailyMealDescription(String detailedDailyMealDescription) {
        this.detailedDailyMealDescription = detailedDailyMealDescription;
    }

}
