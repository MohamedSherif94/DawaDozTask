package com.example.mohamedsherif.dawadoztask.models;

public class City {

    private int mID;
    private String mName;
    private int mTemperature;

    public City() {
    }

    public City(int mID, String mName) {
        this.mID = mID;
        this.mName = mName;
    }

    public City(int mID, String mName, int mTemperature) {
        this.mID = mID;
        this.mName = mName;
        this.mTemperature = mTemperature;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmTemperature() {
        return mTemperature;
    }

    public void setmTemperature(int mTemperature) {
        this.mTemperature = mTemperature;
    }
}
