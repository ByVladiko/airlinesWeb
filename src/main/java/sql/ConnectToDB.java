package sql;

import org.sqlite.JDBC;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectToDB {

    private static Connection connection;
    private static String dbType;
    private static String pathToDb;
    private static String dbName;
    private static String dbSchema;

    private ConnectToDB() {
    }

    public static void registryDriver() {
        try {
            Properties property = new Properties();
            property.load(new FileInputStream("database.properties"));

            dbType = property.getProperty("DB_TYPE");
            pathToDb = property.getProperty("RELATIVE_PATH_TO_DB");
            dbName = property.getProperty("DB_NAME");
            dbSchema = property.getProperty("DB_SCHEMA");

            if (dbType.equals("sqlite")) {
                DriverManager.registerDriver(new JDBC());
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                String path = String.format("jdbc:%s:%s%s", ConnectToDB.dbType, ConnectToDB.pathToDb, ConnectToDB.dbName);
                connection = DriverManager.getConnection(path);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
