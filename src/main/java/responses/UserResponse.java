package responses;

import com.google.gson.annotations.SerializedName;
import model.User;

public class UserResponse extends Response {

    @SerializedName("user")
    private User user;

    @Override
    public UserResponse setStatus(int status) {
        super.setStatus(status);
        return this;
    }

    public UserResponse setUser(User user) {
        this.user = user;
        return this;
    }
}
