package model;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class User {
    @SerializedName("user_id")
    private UUID mUserId;
    @SerializedName("user_name")
    private String mName;
    @SerializedName("user_photo_url")
    private String mPhotoUrl;

    private int vkId;

    public User(UUID mUserId, String mName) {
        this.mUserId = mUserId;
        this.mName = mName;
    }

    public User(String mName, int vkId) {
        this.mName = mName;
        this.vkId = vkId;
    }

    public int getVkId() {
        return vkId;
    }

    public String getmName() {
        return mName;
    }
}
