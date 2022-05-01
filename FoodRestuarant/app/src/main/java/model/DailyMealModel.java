package model;

public class DailyMealModel {
    int dailyMealImage;
    String dailyMealName;
    String dailyMealDescription;

    public int getDailyMealImage() {
        return dailyMealImage;
    }

    public void setDailyMealImage(int dailyMealImage) {
        this.dailyMealImage = dailyMealImage;
    }

    public String getDailyMealName() {
        return dailyMealName;
    }

    public void setDailyMealName(String dailyMealName) {
        this.dailyMealName = dailyMealName;
    }

    public String getDailyMealDescription() {
        return dailyMealDescription;
    }

    public void setDailyMealDescription(String dailyMealDescription) {
        this.dailyMealDescription = dailyMealDescription;
    }

    public DailyMealModel(int dailyMealImage, String dailyMealName, String dailyMealDescription) {
        this.dailyMealImage = dailyMealImage;
        this.dailyMealName = dailyMealName;
        this.dailyMealDescription = dailyMealDescription;
    }
}
