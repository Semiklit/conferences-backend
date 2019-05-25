package responses;

import com.google.gson.annotations.SerializedName;
import model.Section;

import java.util.List;

public class SectionsResponse extends Response{

    @SerializedName("SectionsList")
    private List<Section> sections;

    @Override
    public SectionsResponse setStatus(int status) {
        super.setStatus(status);
        return this;
    }

    public SectionsResponse setSections(List<Section> sections) {
        this.sections = sections;
        return this;
    }
}
