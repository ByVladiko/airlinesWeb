package service.dao;

import model.Flight;
import org.junit.Assert;
import org.junit.Test;
import service.SQLTestOperations;
import util.GeneratorSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FlightDAOTest extends SQLTestOperations {

    @Test
    public void testCreate() throws SQLException {

        Flight expected = createFlight();

        Flight actual = flightDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void testCreateException() throws SQLException {
        Flight test = createFlight();

        Flight flight = GeneratorSQL.getRandomFlight();
        flight.setId(test.getId());

        Assert.assertThrows(SQLException.class, () -> flightDAO.create(connection, flight));

        connection.rollback();
    }

    @Test
    public void testGetById() throws SQLException {

        Flight expected = createFlight();

        Flight actual = flightDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void testGetByIdException() {
        Assert.assertThrows(SQLException.class, () -> flightDAO.getById(connection, UUID.randomUUID().toString()));
    }

    @Test
    public void testUpdate() throws SQLException {

        Flight expected = createFlight();

        expected.setDateOfArrival(GeneratorSQL.getRandomDate());
        expected.setDateOfDeparture(GeneratorSQL.getRandomDate());
        flightDAO.update(connection, expected);

        Flight actual = flightDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void testUpdateException() throws SQLException {
        Flight test = createFlight();

        test.setId(UUID.randomUUID());

        Assert.assertThrows(SQLException.class, () -> flightDAO.update(connection, test));

        connection.rollback();
    }

    @Test
    public void testDelete() throws SQLException {

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
    public void testDeleteException() {
        Assert.assertThrows(SQLException.class, () -> flightDAO.delete(connection, GeneratorSQL.getRandomFlight()));
    }

    @Test
    public void testGetAll() throws SQLException {
        int initialCapacity = 10;
        List<Flight> expected = new ArrayList<>(initialCapacity);

        for (int i = 0; i < initialCapacity; i++) {
            Flight flight = createFlight();
            expected.add(flight);
        }

        List<Flight> actual = flightDAO.getAll(connection);
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());

        connection.rollback();
    }
}
