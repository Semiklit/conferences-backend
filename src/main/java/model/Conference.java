package model;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class Conference {

    @SerializedName("conference_id")
    private UUID mConferenceId;
    @SerializedName("conference_title")
    private String mTitle;
    @SerializedName("conference_desc")
    private String mDesc;

    public Conference(UUID mConferenceId, String mTitle, String mDesc) {
        this.mConferenceId = mConferenceId;
        this.mTitle = mTitle;
        this.mDesc = mDesc;
    }
}
