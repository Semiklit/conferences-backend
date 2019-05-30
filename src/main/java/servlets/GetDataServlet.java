package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import data.DataBaseManager;
import model.*;
import responses.DataResponse;
import responses.MessagesResponse;
import responses.NotificationsResponse;
import responses.Response;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class GetDataServlet extends HttpServlet {

    private Gson gson;

    @Override
    public void init() {
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/json; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        switch (req.getParameter(Api.PARAMETER_ACTION)) {
            case Api.ACTION_GET_CONFERENCE_LIST:
                resp.getWriter().println(getConferences(req));
                break;
            case Api.ACTION_GET_CONFERENCE:

                break;
            case Api.ACTION_GET_CONFERENCE_OWEND:
                resp.getWriter().println(getOwenByUserConferences(req));
                break;
            case Api.ACTION_GET_CONFERENCE_FAVOURITS:
                resp.getWriter().println(getUserFavouriteConferences(req));
                break;
            case Api.ACTION_GET_REPORTS_FOR_SUBMIT:

                break;
            case Api.ACTION_GET_MESSAGES:
                resp.getWriter().println(getConferenceMessages(req));
                break;
            case Api.ACTION_GET_NOTIFICATIONS:
                resp.getWriter().println(getUserNotifications(req));
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    private String getConferenceMessages(HttpServletRequest req) {
        UUID conferenceId = UUID.fromString(req.getParameter(Api.PARAMETER_CONFERENCE_ID));
        List<Message> messages = DataBaseManager.getManager().getConferenceMessages(conferenceId);
        return gson.toJson(new MessagesResponse().setStatus(Response.STATUS_OK).setMessages(messages));
    }

    private String getUserNotifications(HttpServletRequest req) {
        UUID userId = UUID.fromString(req.getParameter(Api.PARAMETER_USER_ID));
        List<Notification> notifications = DataBaseManager.getManager().getUserNotifications(userId);
        return gson.toJson(new NotificationsResponse().setStatus(Response.STATUS_OK).setNotifications(notifications));
    }

    private String getOwenByUserConferences(HttpServletRequest req) {
        UUID token = UUID.fromString(req.getHeader(Api.HEADER_AUTH));
        UUID userId = DataBaseManager.getManager().getCurrentUserId(token);
        List<Conference> conferenceList = DataBaseManager.getManager().getOwenedByUserConferences(userId);
        return getDataResponseForConferencesList(conferenceList);
    }

    private String getUserFavouriteConferences(HttpServletRequest req) {
        UUID token = UUID.fromString(req.getHeader(Api.HEADER_AUTH));
        UUID userId = DataBaseManager.getManager().getCurrentUserId(token);
        List<Conference> conferenceList = DataBaseManager.getManager().getUserFavouriteConferences(userId);
        return getDataResponseForConferencesList(conferenceList);
    }

    private String getConferences(HttpServletRequest req) {
        List<Conference> conferenceList = DataBaseManager.getManager().getConferences();
        return getDataResponseForConferencesList(conferenceList);
    }

    private String getDataResponseForConferencesList(List<Conference> conferenceList) {
        List<Section> sectionList = new ArrayList<>();
        for (Conference conference : conferenceList) {
            List<Section> sections = DataBaseManager.getManager().getSections(conference.getConferenceId().toString());
            List<UUID> sectionsIds = sections.stream().map((Section::getSectionId)).collect(Collectors.toList());
            conference.setSectionsIds(sectionsIds);
            sectionList.addAll(sections);
        }
        List<Report> reportList = new ArrayList<>();
        for (Section section : sectionList) {
            List<Report> reports = DataBaseManager.getManager().getReports(section.getSectionId().toString());
            List<UUID> reportIds = reports.stream().map((Report::getReportId)).collect(Collectors.toList());
            section.setReportsIds(reportIds);
            reportList.addAll(reports);
        }
        List<User> userList = new ArrayList<>();
        for (Report report : reportList) {
            userList.add(DataBaseManager.getManager().getUser(report.getUserId()));
        }
        return gson.toJson(new DataResponse()
                .setStatus(Response.STATUS_OK)
                .setConferenceList(conferenceList)
                .setSections(sectionList)
                .setReports(reportList)
                .setUsers(userList));
    }
}
