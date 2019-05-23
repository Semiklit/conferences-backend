package responses;

import com.google.gson.annotations.SerializedName;
import model.Response;

import java.util.UUID;

public class LoginResponse extends Response {
    @SerializedName("Token")
    private UUID token;

    @Override
    public LoginResponse setStatus(int status) {
        super.setStatus(status);
        return this;
    }

    public LoginResponse setToken(UUID token) {
        this.token = token;
        return this;
    }
}
