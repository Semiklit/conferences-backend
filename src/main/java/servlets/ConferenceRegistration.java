package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import data.DataBaseManager;
import data.DateUtil;
import model.Conference;
import model.Section;
import requests.CreateConferenceRequest;
import responses.Response;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;
import java.util.stream.Collectors;

public class ConferenceRegistration extends HttpServlet {

    private Connection connection;
    private Gson gson;

    @Override
    public void init() {
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        connection = DataBaseManager.getManager().getConnection();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        switch (req.getParameter(Api.PARAMETER_ACTION)) {
            case Api.ACTION_CREATE_CONFERENCE:
                resp.getWriter().println(regConference(req));
                break;
            case Api.ACTION_EDIT_CONFERENCE:
//                resp.getWriter().println(regConference(req));
                break;
            case Api.ACTION_CREATE_REPORT:
//                resp.getWriter().println(regConference(req));
                break;
        }
    }

    private String regConference(HttpServletRequest req) {
        try {
            String json = req.getReader().lines().collect(Collectors.joining());
            CreateConferenceRequest createConferenceRequest = gson.fromJson(json, CreateConferenceRequest.class);
            Conference conference = createConferenceRequest.getConference();
            UUID conferenceId = UUID.randomUUID();
            connection.createStatement().execute(
                    "INSERT INTO conference_maker.conference.conferences VALUES (" +
                            "'" + conferenceId + "', " +
                            "'" + conference.getTitle() + "', " +
                            "'" + conference.getDesc() + "', " +
                            "'" + DateUtil.formatDate(conference.getStartConference()) + "', " +
                            "'" + DateUtil.formatDate(conference.getStartConference()) + "', " +
                            "'" + DateUtil.formatDate(conference.getEndRegistration()) + "'," +
                            "'" + conference.isPublic() + "'," +
                            "'" + DataBaseManager.getManager().getCurrentUserId(UUID.fromString(req.getHeader(Api.HEADER_AUTH))) + "'," +
                            "'" + conference.getCity() + "'" +
                            ")");
            for (Section section : createConferenceRequest.getSectionList()) {
                connection.createStatement().execute(
                        "INSERT into conference_maker.conference.sections VALUES (" +
                                "'" + UUID.randomUUID() + "'," +
                                "'" + conferenceId + "'," +
                                "'" + section.getmTitle() + "'," +
                                "'" + section.getmDesc() + "')"
                );
            }
            return gson.toJson(new Response().setStatus(Response.STATUS_OK));
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return gson.toJson(new Response().setStatus(Response.STATUS_ERROR));
    }
}
