package model;

public class DailyMealModel {
    int dailyMealImage;
    String dailyMealName;
    String dailyMealDescription;
    String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DailyMealModel(int dailyMealImage, String dailyMealName, String dailyMealDescription, String type) {
        this.dailyMealImage = dailyMealImage;
        this.dailyMealName = dailyMealName;
        this.dailyMealDescription = dailyMealDescription;
        this.type = type;
    }
}
