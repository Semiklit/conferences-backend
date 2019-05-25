package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    DataBaseManager (){
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException ignored) {

        }
    }

    public Connection getConnection() {
        return connection;
    }
}
