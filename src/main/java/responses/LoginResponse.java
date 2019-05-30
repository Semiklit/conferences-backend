package responses;

import com.google.gson.annotations.SerializedName;
import model.User;

import java.util.UUID;

public class LoginResponse extends Response {
    @SerializedName("token")
    private UUID token;
    @SerializedName("user")
    private User user;

    @Override
    public LoginResponse setStatus(int status) {
        super.setStatus(status);
        return this;
    }

    public LoginResponse setToken(UUID token) {
        this.token = token;
        return this;
    }

    public LoginResponse setUser(User user) {
        this.user = user;
        return this;
    }
}
