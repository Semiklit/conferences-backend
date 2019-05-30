package responses;

import com.google.gson.annotations.SerializedName;
import model.Notification;

import java.util.List;

public class NotificationsResponse extends Response {
    @SerializedName("notifications")
    List<Notification> notifications;

    @Override
    public NotificationsResponse setStatus(int status) {
        super.setStatus(status);
        return this;
    }

    public NotificationsResponse setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
        return this;
    }
}
