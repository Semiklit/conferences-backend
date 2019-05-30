package model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.UUID;

public class Section {
    @SerializedName("section_id")
    private UUID mSectionId;
    @SerializedName("conference_id")
    private UUID mConferenceId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("desc")
    private String mDesc;
    @SerializedName("reports_ids")
    List<UUID> reportsIds;

    public Section(UUID mSectionId, UUID mConferenceId, String mTitle, String mDesc) {
        this.mSectionId = mSectionId;
        this.mConferenceId = mConferenceId;
        this.mTitle = mTitle;
        this.mDesc = mDesc;
    }

    public UUID getSectionId() {
        return mSectionId;
    }

    public void setReportsIds(List<UUID> reportsIds) {
        this.reportsIds = reportsIds;
    }
}
