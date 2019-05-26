package requests;

import com.google.gson.annotations.SerializedName;
import model.Conference;
import model.Section;

import java.util.List;

public class CreateConferenceRequest {
    @SerializedName("conference")
    Conference conference;

    @SerializedName("sections_list")
    List<Section> sectionList;


    public Conference getConference() {
        return conference;
    }

    public List<Section> getSectionList() {
        return sectionList;
    }
}
