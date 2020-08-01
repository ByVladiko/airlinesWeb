package service;

import org.sqlite.JDBC;
import org.sqlite.SQLiteConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnectTest {

    private static String path;
    private static String dbType;
    private static String pathToDb;
    private static String dbSchema;
    private static SQLiteConfig sqLiteConfig;

    private DatabaseConnectTest() {
    }

    public static void registryDriver() {
        try {
            Properties property = new Properties();
            property.load(DatabaseConnectTest.class.getClassLoader().getResource("database.properties").openStream());

            dbType = property.getProperty("DB_TYPE");
            pathToDb = property.getProperty("RELATIVE_PATH_TO_DB");
            dbSchema = property.getProperty("DB_SCHEMA");

            sqLiteConfig = new SQLiteConfig();
            sqLiteConfig.enforceForeignKeys(true);

            if (dbType.equals("sqlite")) {
                DriverManager.registerDriver(new JDBC());
                path = String.format("jdbc:%s:%s",
                        DatabaseConnectTest.dbType,
                        DatabaseConnectTest.pathToDb);
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(path, sqLiteConfig.toProperties());
    }

    public static void executeSqlScriptFile(final Connection connection, Path path) {
        try {
            FileReader fileReader = new FileReader(path.toFile());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            StringBuilder command = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                command.append(line + "\n");
                if (line.contains(";")) {
                    try (Statement statement = connection.createStatement()) {
                        statement.execute(command.toString());
                        command = new StringBuilder();
                    }
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
