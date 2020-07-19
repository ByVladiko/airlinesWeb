package service.dao;

import model.Route;
import org.junit.Assert;
import org.junit.Test;
import service.MainTestOperations;
import util.GeneratorSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteDAOTest extends MainTestOperations {

    @Test
    public void create() throws SQLException {
        connection.setAutoCommit(false);

        Route expected = createRoute();

        Route actual = routeDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void getById() throws SQLException {
        connection.setAutoCommit(false);

        Route expected = createRoute();

        Route actual = routeDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void update() throws SQLException {
        connection.setAutoCommit(false);

        Route expected = createRoute();

        expected.setStartPoint(GeneratorSQL.getRandomString());
        expected.setEndPoint(GeneratorSQL.getRandomString());
        routeDAO.update(connection, expected);

        Route actual = routeDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void delete() throws SQLException {
        connection.setAutoCommit(false);

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
    public void getAll() throws SQLException {
        connection.setAutoCommit(false);

        List<Route> expected = new ArrayList<>(10);
        for (int i = 0; i < expected.size(); i++) {
            Route route = createRoute();
            expected.add(route);
        }

        List<Route> actual = routeDAO.getAll(connection);
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());

        connection.rollback();
    }
}
