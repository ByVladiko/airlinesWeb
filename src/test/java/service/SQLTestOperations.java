package service;

import dao.impl.*;
import model.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import util.GeneratorSQL;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class SQLTestOperations {

    protected static Connection connection;
    protected final RouteDAO routeDAO = new RouteDAO();
    protected final FlightDAO flightDAO = new FlightDAO();
    protected final AirshipDAO airshipDAO = new AirshipDAO();
    protected final TicketDAO ticketDAO = new TicketDAO();
    protected final ClientDAO clientDAO = new ClientDAO();

    @BeforeClass
    public static void registryDriver() throws SQLException, IOException {
        DatabaseConnectTest.registryDriver();
        connection = DatabaseConnectTest.getConnection();
        Files.walk(Paths.get("src/test/resources/sql"))
                .filter(Files::isRegularFile)
                .collect(Collectors.toList())
                .forEach(pathToFile -> DatabaseConnectTest.executeSqlScriptFile(connection, pathToFile));
        connection.setAutoCommit(false);
        System.out.println("Successful database connection");
    }

    @AfterClass
    public static void finish() throws SQLException {
        connection.rollback();
        System.out.println("Test finished");
    }

    protected Route createRoute() throws SQLException {
        Route route = GeneratorSQL.getRandomRoute();
        routeDAO.create(connection, route);
        return route;
    }

    protected Airship createAirship() throws SQLException {
        Airship airship = GeneratorSQL.getRandomAirship();
        airshipDAO.create(connection, airship);
        return airship;
    }

    protected Flight createFlight() throws SQLException {
        Flight expected = GeneratorSQL.getRandomFlight();
        airshipDAO.create(connection, expected.getAirship());
        routeDAO.create(connection, expected.getRoute());
        flightDAO.create(connection, expected);
        return expected;
    }

    protected Client createClient() throws SQLException {
        Client client = GeneratorSQL.getRandomClient();
        clientDAO.create(connection, client);
        return client;
    }

    protected Ticket createTicket() throws SQLException {
        Ticket ticket = GeneratorSQL.getRandomTicket();
        airshipDAO.create(connection, ticket.getFlight().getAirship());
        routeDAO.create(connection, ticket.getFlight().getRoute());
        flightDAO.create(connection, ticket.getFlight());
        ticketDAO.create(connection, ticket);
        return ticket;
    }
}
