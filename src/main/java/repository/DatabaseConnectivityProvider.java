package repository;

import org.sqlite.JDBC;
import org.sqlite.SQLiteConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectivityProvider {

    private static Connection connection;
    private static String path;
    private static String dbType;
    private static String pathToDb;
    private static String dbName;
    private static String dbSchema;
    private static SQLiteConfig sqLiteConfig;

    private static final String PATH_TO_CONNECTION_PROPERTIES = "../../../database.properties";

    private DatabaseConnectivityProvider() {
    }

    public static void registryDriver() {
        try {
            Properties property = new Properties();
            property.load(new FileInputStream(PATH_TO_CONNECTION_PROPERTIES));

            dbType = property.getProperty("DB_TYPE");
            pathToDb = property.getProperty("RELATIVE_PATH_TO_DB");
            dbName = property.getProperty("DB_NAME");
            dbSchema = property.getProperty("DB_SCHEMA");

            sqLiteConfig = new SQLiteConfig();
            sqLiteConfig.enforceForeignKeys(true);

            if (dbType.equals("sqlite")) {
                DriverManager.registerDriver(new JDBC());
                path = String.format("jdbc:%s:%s%s", DatabaseConnectivityProvider.dbType, DatabaseConnectivityProvider.pathToDb, DatabaseConnectivityProvider.dbName);
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(path, sqLiteConfig.toProperties());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
