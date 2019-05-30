package responses;


import model.Conference;
import model.Report;
import model.Section;
import model.User;

import java.util.List;

public class DataResponse extends Response {
    private List<Conference> conferenceList;
    private List<Section> sections;
    private List<Report> reports;
    private List<User> users;

    @Override
    public DataResponse setStatus(int status) {
        super.setStatus(status);
        return this;
    }

    public DataResponse setConferenceList(List<Conference> conferenceList) {
        this.conferenceList = conferenceList;
        return this;
    }

    public DataResponse setSections(List<Section> sections) {
        this.sections = sections;
        return this;
    }

    public DataResponse setReports(List<Report> reports) {
        this.reports = reports;
        return this;
    }

    public DataResponse setUsers(List<User> users) {
        this.users = users;
        return this;
    }
}
