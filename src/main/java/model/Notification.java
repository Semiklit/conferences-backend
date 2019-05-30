package model;

import com.google.gson.annotations.SerializedName;

public class Notification {
    @SerializedName("title")
    String title;
    @SerializedName("text")
    String text;

    public Notification(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
