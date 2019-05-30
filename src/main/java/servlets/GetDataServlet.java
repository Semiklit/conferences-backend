package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import data.DataBaseManager;
import data.DateUtil;
import model.Conference;
import model.Report;
import model.Section;
import responses.ConferencesResponse;
import responses.ReportsResponse;
import responses.Response;
import responses.SectionsResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GetDataServlet extends HttpServlet {

    private Connection connection;

    @Override
    public void init() throws ServletException {
        connection = DataBaseManager.getManager().getConnection();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        switch (req.getParameter(Api.PARAMETER_ACTION)) {
            case Api.ACTION_GET_CONFERENCE_LIST:
                resp.getWriter().println(getConferences(req));
                break;
            case Api.ACTION_GET_CONFERENCE_INFO:
                break;
            case Api.ACTION_GET_REPORTS_LIST:
                resp.getWriter().println(getReports(req));
                break;
            case Api.ACTION_GET_REPORT_INFO:
                break;
            case Api.ACTION_GET_SECTION_LIST:
                resp.getWriter().println(getSections(req));
                break;
            case Api.ACTION_GET_SECTION_INFO:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    private String getConferences(HttpServletRequest req) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        try {
            List<Conference> conferenceList = new ArrayList<>();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM conference_maker.conference.conferences");
            while (resultSet.next()) {
                conferenceList.add(new Conference(UUID.fromString(resultSet.getString("conference_id")),
                        resultSet.getString("title"),
                        resultSet.getString("desc"),
                        resultSet.
                        DateUtil.parseDate(resultSet.getString("start")),
                        DateUtil.parseDate(resultSet.getString("end")),
                        DateUtil.parseDate(resultSet.getString("registration_end")),
                        resultSet.getBoolean("is_public"),
                        UUID.fromString(resultSet.getString("owner_id")),
                        resultSet.getString("city"),
                        false));
            }
            return gson.toJson(new ConferencesResponse().setStatus(Response.STATUS_OK).setConferences(conferenceList));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gson.toJson(new Response().setStatus(Response.STATUS_ERROR));
    }

    private String getSections(HttpServletRequest req) {
        UUID conferenceId = UUID.fromString(req.getParameter(Api.PARAMETER_CONFERENCE_ID));
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            List<Section> sectionList = new ArrayList<>();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM conference_maker.conference.sections WHERE conference_id = '" + conferenceId + "'");
            while (resultSet.next()) {
                sectionList.add(new Section(UUID.fromString(resultSet.getString("section_id")),
                        UUID.fromString(resultSet.getString("conference_id")),
                        resultSet.getString("name"),
                        resultSet.getString("desc")));
            }
            return gson.toJson(new SectionsResponse().setStatus(Response.STATUS_OK).setSections(sectionList));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gson.toJson(new Response().setStatus(Response.STATUS_ERROR));
    }

    private String getReports(HttpServletRequest req) {
        UUID sectionId = UUID.fromString(req.getParameter(Api.PARAMETER_SECTION_ID));
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            List<Report> reportList = new ArrayList<>();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM conference_maker.conference.reports WHERE section_id = '" + sectionId + "'");
            while (resultSet.next()) {
                reportList.add(new Report(UUID.fromString(resultSet.getString("report_id")),
                        UUID.fromString(resultSet.getString("user_id")),
                        UUID.fromString(resultSet.getString("section_id")),
                        resultSet.getString("title"),
                        resultSet.getString("desc")));
            }
            return gson.toJson(new ReportsResponse().setStatus(Response.STATUS_OK).setReports(reportList));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gson.toJson(new Response().setStatus(Response.STATUS_ERROR));
    }
}
