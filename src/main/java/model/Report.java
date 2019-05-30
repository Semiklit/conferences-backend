package model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.UUID;

public class Report {

    @SerializedName("report_id")
    private UUID mReportId;
    @SerializedName("user_id")
    private UUID mUserId;
    @SerializedName("section_id")
    private UUID mSectionId;
    @SerializedName("report_title")
    private String mTitle;
    @SerializedName("report_desc")
    private String mDesc;
    @SerializedName("report_arrive_date")
    private Date mArriveDate;
    @SerializedName("report_leave_date")
    private Date mLeaveDate;
    @SerializedName("report_time")
    private Date mTime;

    public Report(UUID mReportId, UUID mUserId, UUID mSectionId, String mTitle, String mDesc, Date mArriveDate, Date mLeaveDate, Date mTime) {
        this.mReportId = mReportId;
        this.mUserId = mUserId;
        this.mSectionId = mSectionId;
        this.mTitle = mTitle;
        this.mDesc = mDesc;
        this.mArriveDate = mArriveDate;
        this.mLeaveDate = mLeaveDate;
        this.mTime = mTime;
    }

    public Report(UUID mReportId, UUID mUserId, UUID mSectionId, String mTitle, String mDesc) {
        this.mReportId = mReportId;
        this.mUserId = mUserId;
        this.mSectionId = mSectionId;
        this.mTitle = mTitle;
        this.mDesc = mDesc;
    }

    public UUID getReportId() {
        return mReportId;
    }

    public UUID getUserId() {
        return mUserId;
    }
}
