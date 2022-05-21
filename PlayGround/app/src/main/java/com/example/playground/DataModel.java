package com.example.playground;

public class DataModel {

    public String androidVersion, androidName;

    public DataModel(String androidVersion, String androidName) {
        this.androidVersion = androidVersion;
        this.androidName = androidName;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getAndroidName() {
        return androidName;
    }

    public void setAndroidName(String androidName) {
        this.androidName = androidName;
    }
}
