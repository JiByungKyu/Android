package com.example.user.myapplication;

/**
 * Created by USER on 2017-08-24.
 */

public class AnalogActivityItem {
    private String analogID;
    private int value;

    public void setValue(int value) {
        this.value = value;
    }

    public void setAnalogID(String analogID) {
        this.analogID = analogID;
    }

    public int getValue() {
        return value;
    }

    public String getAnalogID() {
        return analogID;
    }
}
