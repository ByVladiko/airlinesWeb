package service.dao;

import model.Route;
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

public class RouteDAOTest extends SQLTestOperations {

    @Test
    public void testCreate() throws SQLException {
        Route expected = createRoute();

        Route actual = routeDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void testCreatException() throws SQLException {
        Route test = createRoute();

        Route route = GeneratorSQL.getRandomRoute();
        route.setId(test.getId());

        Assert.assertThrows(SQLException.class, () -> routeDAO.create(connection, route));

        connection.rollback();
    }

    @Test
    public void testGetById() throws SQLException {

        Route expected = createRoute();

        Route actual = routeDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void testGetByIdException() {
        Assert.assertThrows(SQLException.class, () -> routeDAO.getById(connection, UUID.randomUUID().toString()));
    }

    @Test
    public void testUpdate() throws SQLException {

        Route expected = createRoute();

        expected.setStartPoint(GeneratorSQL.getRandomString());
        expected.setEndPoint(GeneratorSQL.getRandomString());
        routeDAO.update(connection, expected);

        Route actual = routeDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void testUpdateException() throws SQLException {
        Route test = createRoute();

        test.setId(UUID.randomUUID());

        Assert.assertThrows(SQLException.class, () -> routeDAO.update(connection, test));

        connection.rollback();
    }

    @Test
    public void testDelete() throws SQLException {

        Route route = createRoute();
        routeDAO.delete(connection, route);

        try (PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM route WHERE id = ?")) {
            statement.setString(1, route.getId().toString());
            ResultSet resultSet = statement.executeQuery();

            boolean result = resultSet.next();
            Assert.assertFalse(result);

            connection.rollback();
        }
    }

    @Test
    public void testDeleteException() {
        Assert.assertThrows(SQLException.class, () -> routeDAO.delete(connection, GeneratorSQL.getRandomRoute()));
    }

    @Test
    public void testGetAll() throws SQLException {
        int initialCapacity = 10;
        List<Route> expected = new ArrayList<>(initialCapacity);

        for (int i = 0; i < initialCapacity; i++) {
            Route route = createRoute();
            expected.add(route);
        }

        List<Route> actual = routeDAO.getAll(connection);
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());

        connection.rollback();
    }
}
