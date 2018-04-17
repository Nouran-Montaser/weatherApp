package com.example.lenovo.weatherapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lenovo on 3/28/2018.
 */

public class Weather {

    @SerializedName("localtime")
    private String localtime;
    @SerializedName("temp_c")
    private String temp_c;
    @SerializedName("text")
    private String text;

    public String getLocaltime() {
        return localtime;
    }

    public void setLocaltime(String localtime) {
        this.localtime = localtime;
    }

    public String getTemp_c() {
        return temp_c;
    }

    public void setTemp_c(String temp_c) {
        this.temp_c = temp_c;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
