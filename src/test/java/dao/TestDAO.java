package dao;

import dao.impl.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDAO {

    static Connection connection;
    final RouteDAO routeDAO = new RouteDAO();
    final FlightDAO flightDAO = new FlightDAO();
    final AirshipDAO airshipDAO = new AirshipDAO();
    final TicketDAO ticketDAO = new TicketDAO();
    final ClientDAO clientDAO = new ClientDAO();

    @BeforeClass
    public static void registryDriver() throws SQLException {
        DatabaseConnect.registryDriver();
        connection = DatabaseConnect.getConnection();
        System.out.println("Successful database connection");
    }

    @AfterClass
    public static void finish() throws SQLException {
        connection.rollback();
        System.out.println("Test completed");
    }

}
