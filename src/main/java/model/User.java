package model;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class User {
    @SerializedName("user_id")
    private UUID mUserId;
    @SerializedName("user_name")
    private String mName;
    @SerializedName("user_surname")
    private String mSurname;
    @SerializedName("user_photo_url")
    private String mPhotoUrl;

    public User(UUID mUserId, String mName, String mSurname, String mPhotoUrl) {
        this.mUserId = mUserId;
        this.mName = mName;
        this.mSurname = mSurname;
        this.mPhotoUrl = mPhotoUrl;
    }
}
