import dao.impl.AirshipDAO;
import dao.impl.ClientDAO;
import dao.impl.FlightDAO;
import dao.impl.RouteDAO;
import model.Airship;
import model.Client;
import model.Flight;
import model.Route;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.DatabaseConnectivityProvider;
import util.DateConverter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class TestDAO {

    private static Connection connection;
    private static final String PATH_TO_CONNECTION_PROPERTIES = "database.properties";

    @BeforeClass
    public static void registryDriver() throws SQLException, IOException {
        Properties property = new Properties();
        property.load(new FileInputStream(PATH_TO_CONNECTION_PROPERTIES));
        property.setProperty("DB_NAME", "testAirlines.db");
        property.setProperty("RELATIVE_PATH_TO_DB", "tests/");
        property.store(new FileOutputStream(PATH_TO_CONNECTION_PROPERTIES), null);
        DatabaseConnectivityProvider.registryDriver();
        connection = DatabaseConnectivityProvider.getConnection();
        System.out.println("Connected with database");
    }

    @AfterClass
    public static void returnToDefault() throws IOException, SQLException {
        Properties property = new Properties();
        property.load(new FileInputStream(PATH_TO_CONNECTION_PROPERTIES));
        property.setProperty("DB_NAME", "airlines.db");
        property.setProperty("RELATIVE_PATH_TO_DB", "../../../");
        property.store(new FileOutputStream(PATH_TO_CONNECTION_PROPERTIES), null);
        connection.rollback();
        System.out.println("Test completed");
    }

    @Test
    public void getAllClients() throws SQLException {
        ClientDAO clientDAO = new ClientDAO();
        connection.setAutoCommit(false);
        List<Client> expected = new ArrayList<>();
        expected.add(new Client("TestName", "TestMiddleName", "TestLastName", 5000));
        expected.add(new Client("Test2Name", "Test2MiddleName", "Test2LastName", 10000));
        for (int i = 0; i < expected.size(); i++) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO client VALUES(?, ?, ?, ?, ?)")) {
                Client client = expected.get(i);
                preparedStatement.setString(1, client.getId().toString());
                preparedStatement.setString(2, client.getFirstName());
                preparedStatement.setString(3, client.getMiddleName());
                preparedStatement.setString(4, client.getLastName());
                preparedStatement.setFloat(5, client.getBill());
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        List<Client> actual = clientDAO.getAll(connection);
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());
        connection.rollback();
    }

    @Test
    public void getAllAirships() throws SQLException {
        AirshipDAO airshipDAO = new AirshipDAO();
        connection.setAutoCommit(false);
        List<Airship> expected = new ArrayList<>();
        expected.add(new Airship("TestAirship", 10, 10, 10));
        expected.add(new Airship("TestAirship", 5, 5, 5));
        for (int i = 0; i < expected.size(); i++) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO airship VALUES(?, ?, ?, ?, ?)")) {
                Airship airship = expected.get(i);
                preparedStatement.setString(1, airship.getId().toString());
                preparedStatement.setString(2, airship.getModel());
                preparedStatement.setInt(3, airship.getEconomyCategory());
                preparedStatement.setInt(4, airship.getBusinessCategory());
                preparedStatement.setInt(5, airship.getPremiumCategory());
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        List<Airship> actual = airshipDAO.getAll(connection);
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());
        connection.rollback();
    }

    @Test
    public void getAllRoutes() throws SQLException {
        RouteDAO routeDAO = new RouteDAO();
        connection.setAutoCommit(false);
        List<Route> expected = new ArrayList<>();
        expected.add(new Route("TestStartPoint", "TestEndPoint"));
        expected.add(new Route("TestStartPoint1", "TestEndPoint1"));
        for (int i = 0; i < expected.size(); i++) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO route VALUES(?, ?, ?)")) {
                Route route = expected.get(i);
                preparedStatement.setString(1, route.getId().toString());
                preparedStatement.setString(2, route.getStartPoint());
                preparedStatement.setString(3, route.getEndPoint());
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        List<Route> actual = routeDAO.getAll(connection);
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());
        connection.rollback();
    }

    @Test
    public void getAllFlights() throws SQLException {
        FlightDAO flightDAO = new FlightDAO();
        connection.setAutoCommit(false);
        List<Flight> expected = new ArrayList<>();
        expected.add(new Flight(new Date(29368498236L), new Date(29368498235L),
                new Airship("Test", 10, 10, 10),
                new Route("Test", "Test")));
        expected.add(new Flight(new Date(29368498228L), new Date(29368498229L),
                new Airship("Test1", 5, 5, 5),
                new Route("Test1", "Test1")));
        for (int i = 0; i < expected.size(); i++) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO flight VALUES(?, ?, ?, ?, ?)")) {
                Flight flight = expected.get(i);
                preparedStatement.setString(1, flight.getId().toString());
                preparedStatement.setString(2, DateConverter.convert(flight.getDateOfDeparture()));
                preparedStatement.setString(3, DateConverter.convert(flight.getDateOfArrival()));
                preparedStatement.setString(4, flight.getAirship().getId().toString());
                preparedStatement.setString(5, flight.getRoute().getId().toString());
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        List<Flight> actual = flightDAO.getAll(connection);
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());
        connection.rollback();
    }

}
