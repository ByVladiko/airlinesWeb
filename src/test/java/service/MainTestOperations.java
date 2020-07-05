package service;

import dao.impl.*;
import model.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import util.GeneratorSQL;

import java.sql.Connection;
import java.sql.SQLException;

public class MainTestOperations {

    protected static Connection connection;
    protected final RouteDAO routeDAO = new RouteDAO();
    protected final FlightDAO flightDAO = new FlightDAO();
    protected final AirshipDAO airshipDAO = new AirshipDAO();
    protected final TicketDAO ticketDAO = new TicketDAO();
    protected final ClientDAO clientDAO = new ClientDAO();

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

    protected Route createRoute() {
        Route route = GeneratorSQL.getRandomRoute();
        routeDAO.create(connection, route);
        return route;
    }

    protected Airship createAirship() {
        Airship airship = GeneratorSQL.getRandomAirship();
        airshipDAO.create(connection, airship);
        return airship;
    }

    protected Flight createFlight() {
        Flight expected = GeneratorSQL.getRandomFlight();
        airshipDAO.create(connection, expected.getAirship());
        routeDAO.create(connection, expected.getRoute());
        flightDAO.create(connection, expected);
        return expected;
    }

    protected Client createClient() {
        Client client = GeneratorSQL.getRandomClient();
        clientDAO.create(connection, client);
        return client;
    }

    protected Ticket createTicket() {
        Ticket ticket = GeneratorSQL.getRandomTicket();
        airshipDAO.create(connection, ticket.getFlight().getAirship());
        routeDAO.create(connection, ticket.getFlight().getRoute());
        flightDAO.create(connection, ticket.getFlight());
        ticketDAO.create(connection, ticket);
        return ticket;
    }
}
