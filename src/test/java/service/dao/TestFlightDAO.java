package service.dao;

import model.Flight;
import org.junit.Assert;
import org.junit.Test;
import service.MainTestOperations;
import util.GeneratorSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestFlightDAO extends MainTestOperations {

    @Test
    public void create() throws SQLException {
        connection.setAutoCommit(false);

        Flight expected = createFlight();

        Flight actual = flightDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void getById() throws SQLException {
        connection.setAutoCommit(false);

        Flight expected = createFlight();

        Flight actual = flightDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void update() throws SQLException {
        connection.setAutoCommit(false);

        Flight expected = createFlight();

        expected.setDateOfArrival(GeneratorSQL.getRandomDate());
        expected.setDateOfDeparture(GeneratorSQL.getRandomDate());
        flightDAO.update(connection, expected);

        Flight actual = flightDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void delete() throws SQLException {
        connection.setAutoCommit(false);

        Flight flight = createFlight();

        flightDAO.delete(connection, flight);
        try (PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM flight WHERE id = ?")) {
            statement.setString(1, flight.getId().toString());
            ResultSet resultSet = statement.executeQuery();

            boolean result = resultSet.next();
            Assert.assertFalse(result);

            connection.rollback();
        }
    }

    @Test
    public void getAll() throws SQLException {
        connection.setAutoCommit(false);

        List<Flight> expected = new ArrayList<>(10);

        for (int i = 0; i < expected.size(); i++) {
            Flight flight = createFlight();
            expected.add(flight);
        }

        List<Flight> actual = flightDAO.getAll(connection);
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());

        connection.rollback();
    }
}
