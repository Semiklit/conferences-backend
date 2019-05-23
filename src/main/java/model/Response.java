package model;

import com.google.gson.annotations.SerializedName;

public class Response {
    public static final int STATUS_OK = 0;
    public static final int STATUS_WRONG_TOKEN = 1;
    public static final int STATUS_USER_NOT_FOUND = 2;
    public static final int STATUS_BAD_REQUEST = 3;
    public static final int STATUS_ERROR = 4;

    @SerializedName("Status")
    private int status;

    public Response setStatus(int status) {
        this.status = status;
        return this;
    }
}
