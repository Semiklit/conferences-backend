package responses;

import com.google.gson.annotations.SerializedName;
import model.Message;

import java.util.List;

public class MessagesResponse extends Response {
    @SerializedName("notifications")
    List<Message> messages;

    @Override
    public MessagesResponse setStatus(int status) {
        super.setStatus(status);
        return this;
    }

    public MessagesResponse setMessages(List<Message> messages) {
        this.messages = messages;
        return this;
    }
}
