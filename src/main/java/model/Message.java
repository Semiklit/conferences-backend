package model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Message {
    @SerializedName("title")
    private String title;
    @SerializedName("text")
    private String text;
    @SerializedName("desc")
    private Date date;

    public Message(String title, String text, Date date) {
        this.title = title;
        this.text = text;
        this.date = date;
    }
}
