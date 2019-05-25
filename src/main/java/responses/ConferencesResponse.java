package responses;

import com.google.gson.annotations.SerializedName;
import model.Conference;

import java.util.List;

public class ConferencesResponse extends Response {

    @SerializedName("ConferencesList")
    private List<Conference> conferences;

    @Override
    public ConferencesResponse setStatus(int status) {
        super.setStatus(status);
        return this;
    }

    public ConferencesResponse setConferences(List<Conference> conferences) {
        this.conferences = conferences;
        return this;
    }
}
