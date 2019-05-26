package model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.UUID;

public class Conference {

    @SerializedName("conference_id")
    private UUID mConferenceId;
    @SerializedName("conference_title")
    private String mTitle;
    @SerializedName("conference_desc")
    private String mDesc;
    @SerializedName("conference_start")
    private Date mStartConference;
    @SerializedName("conference_end")
    private Date mEndConference;
    @SerializedName("conference_registration_end")
    private Date mEndRegistration;


    public Conference(UUID mConferenceId, String mTitle, String mDesc, Date mStartConference, Date mEndConference, Date mEndRegistration) {
        this.mConferenceId = mConferenceId;
        this.mTitle = mTitle;
        this.mDesc = mDesc;
        this.mStartConference = mStartConference;
        this.mEndConference = mEndConference;
        this.mEndRegistration = mEndRegistration;
    }


    public UUID getmConferenceId() {
        return mConferenceId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDesc() {
        return mDesc;
    }

    public Date getmStartConference() {
        return mStartConference;
    }

    public Date getmEndConference() {
        return mEndConference;
    }

    public Date getmEndRegistration() {
        return mEndRegistration;
    }
}
