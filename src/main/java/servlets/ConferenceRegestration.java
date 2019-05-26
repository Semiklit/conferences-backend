package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import data.DataBaseManager;
import data.DateUtil;
import model.Conference;
import requests.CreateConferenceRequest;
import responses.Response;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class ConferenceRegestration extends HttpServlet {

    private Connection connection;

    @Override
    public void init() throws ServletException {
        connection = DataBaseManager.getManager().getConnection();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        switch (req.getParameter(Api.PARAMETER_ACTION)) {
            case Api.ACTION_CREATE_CONFERENCE:
                resp.getWriter().println(regConference(req));
                break;
        }
    }

    private String regConference(HttpServletRequest req) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        try {

            String json = req.getReader().lines().collect(Collectors.joining());
            CreateConferenceRequest createConferenceRequest = gson.fromJson(json, CreateConferenceRequest.class);
            Conference conference = createConferenceRequest.getConference();
            connection.createStatement().execute(
                    "INSERT INTO conference_maker.conference.conferences VALUES (" +
                            "'" + conference.getmConferenceId() + "', " +
                            "'" + conference.getmTitle() + "', " +
                            "'" + conference.getmDesc() + "', " +
                            "'" + DateUtil.formatDate(conference.getmStartConference()) + "', " +
                            "'" + DateUtil.formatDate(conference.getmStartConference()) + "', " +
                            "'" + DateUtil.formatDate(conference.getmEndRegistration()) + "'" +
                            ")");
            return gson.toJson(new Response().setStatus(Response.STATUS_OK));
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return gson.toJson(new Response().setStatus(Response.STATUS_ERROR));
    }
}
