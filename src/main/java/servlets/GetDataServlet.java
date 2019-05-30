package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import data.DataBaseManager;
import model.Conference;
import model.Report;
import model.Section;
import responses.*;

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
import java.util.stream.Collectors;

public class GetDataServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/json; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        if (Api.ACTION_GET_CONFERENCE_LIST.equals(req.getParameter(Api.PARAMETER_ACTION))) {
            resp.getWriter().println(getData(req));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    private String getData(HttpServletRequest req) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        List<Conference> conferenceList = DataBaseManager.getManager().getConferences(req.getParameter(Api.PARAMETER_CONFERENCE_ID));
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
        return gson.toJson(new DataResponse().setStatus(Response.STATUS_OK).setConferenceList(conferenceList).setSections(sectionList).setReports(reportList));
    }
}
