package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDB {

    private static final String PATH_TO_DB = "airlines.db";

    private static Connection connection;

    private ConnectToDB() {
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection("jdbc:sqlite:" + PATH_TO_DB);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
