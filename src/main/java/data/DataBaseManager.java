package data;

import model.Conference;
import model.Report;
import model.Section;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataBaseManager {

    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String DATABASE_URL = "jdbc:postgresql://localhost/conference_maker";

    private static final String USER = "ns_conference_backend";
    private static final String PASSWORD = "0156";

    private static DataBaseManager manager = new DataBaseManager();

    private Connection connection = null;

    public static DataBaseManager getManager() {
        return manager;
    }

    private DataBaseManager() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException ignored) {

        }
    }

    public Connection getConnection() {
        return connection;
    }

    public List<Conference> getConferences(String conferenceId) {
        try {
            List<Conference> conferenceList = new ArrayList<>();
            ResultSet resultSet;
            if (conferenceId != null) {
                resultSet = connection.createStatement().executeQuery("SELECT * FROM conference_maker.conference.conferences WHERE conference_id = '" + conferenceId + "'");
            } else {
                resultSet = connection.createStatement().executeQuery("SELECT * FROM conference_maker.conference.conferences");
            }
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
            return conferenceList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Section> getSections(String conferenceId) {
        try {
            List<Section> sectionList = new ArrayList<>();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM conference_maker.conference.sections WHERE conference_id = '" + conferenceId + "'");
            while (resultSet.next()) {
                sectionList.add(new Section(UUID.fromString(resultSet.getString("section_id")),
                        UUID.fromString(resultSet.getString("conference_id")),
                        resultSet.getString("name"),
                        resultSet.getString("desc")));
            }
            return sectionList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Report> getReports(String sectionId) {
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
            return reportList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

