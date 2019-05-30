package data;

import model.*;

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

    private static Conference conferenceFromFullResultSet(ResultSet resultSet) {
        try {
            return new Conference(UUID.fromString(resultSet.getString("conference_id")),
                    resultSet.getString("title"),
                    resultSet.getString("desc"),
                    DateUtil.parseDate(resultSet.getString("start")),
                    DateUtil.parseDate(resultSet.getString("end")),
                    DateUtil.parseDate(resultSet.getString("registration_end")),
                    resultSet.getBoolean("is_public"),
                    UUID.fromString(resultSet.getString("owner_id")),
                    resultSet.getString("city"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Conference> conferencesFromFullResultSet(ResultSet resultSet) throws SQLException {
        List<Conference> conferenceList = new ArrayList<>();
        while (resultSet.next()) {
            conferenceList.add(conferenceFromFullResultSet(resultSet));
        }
        return conferenceList;
    }

    public UUID getCurrentUserId(UUID token) {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT user_id FROM conference.tokens WHERE token = '" + token + "'");
            if (resultSet.next()) {
                return UUID.fromString(resultSet.getString("user_id"));
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getUserPushTokens(UUID userId) {
        List<String> pushTokens = new ArrayList<>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT push_token FROM conference.push_tokens WHERE user_id = '" + userId + "'");
            while (resultSet.next()) {
                pushTokens.add(resultSet.getString("push_token"));
            }
            return pushTokens;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<UUID> getUserFavouriteConferenceIds(UUID userId) {
        List<UUID> conferenceIds = new ArrayList<>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT conference_id FROM conference.favourits WHERE user_id = '" + userId + "'");
            while (resultSet.next()) {
                conferenceIds.add(UUID.fromString(resultSet.getString("conference_id")));
            }
            return conferenceIds;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUser(UUID userId) {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT name FROM conference.users WHERE uuid = '" + userId + "'");
            if (resultSet.next()) {
                return new User(userId, resultSet.getString("name"));
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Conference> getOwenedByUserConferences(UUID userId) {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM conference_maker.conference.conferences WHERE owner_id = '" + userId + "'");
            return conferencesFromFullResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Conference> getUserFavouriteConferences(UUID userId) {
        List<UUID> ids = getUserFavouriteConferenceIds(userId);
        if (ids != null && !ids.isEmpty()) {
            List<Conference> conferenceList = new ArrayList<>();
            for (UUID id : ids) {
                conferenceList.add(getConference(id));
            }
            return conferenceList;
        }
        return null;
    }

    public Conference getConference(UUID conferenceId) {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM conference_maker.conference.conferences WHERE conference_id = '" + conferenceId + "'");
            return conferenceFromFullResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Conference> getConferences() {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM conference_maker.conference.conferences");
            return conferencesFromFullResultSet(resultSet);
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

    public List<Message> getConferenceMessages(UUID conferenceId) {
        try {
            List<Message> messages = new ArrayList<>();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM conference_maker.conference.messages WHERE conference_id = '" + conferenceId + "'");
            while (resultSet.next()) {
                messages.add(new Message(resultSet.getString("message_title"),
                        resultSet.getString("message_text"),
                        resultSet.getDate("message_date")));
            }
            return messages;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Notification> getUserNotifications(UUID userId) {
        try {
            List<Notification> notifications = new ArrayList<>();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM conference_maker.conference.notifications WHERE user_id = '" + userId + "'");
            while (resultSet.next()) {
                notifications.add(new Notification(resultSet.getString("message_title"),
                        resultSet.getString("message_text")));
            }
            return notifications;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

