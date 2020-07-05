package service.dao;

import model.Airship;
import org.junit.Assert;
import org.junit.Test;
import service.MainTestOperations;
import util.GeneratorSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestAirshipDAO extends MainTestOperations {

    @Test
    public void create() throws SQLException {
        connection.setAutoCommit(false);

        Airship expected = createAirship();

        Airship actual = airshipDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void getById() throws SQLException {
        connection.setAutoCommit(false);

        Airship expected = createAirship();

        Airship actual = airshipDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void update() throws SQLException {
        connection.setAutoCommit(false);

        Airship expected = createAirship();

        expected.setModel(GeneratorSQL.getRandomString());
        expected.setBusinessCategory(GeneratorSQL.getRandomInt(5, 50));
        expected.setEconomyCategory(GeneratorSQL.getRandomInt(5, 50));
        expected.setPremiumCategory(GeneratorSQL.getRandomInt(5, 50));
        airshipDAO.update(connection, expected);

        Airship actual = airshipDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void delete() throws SQLException {
        connection.setAutoCommit(false);

        Airship airship = createAirship();
        airshipDAO.delete(connection, airship);

        try (PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM airship WHERE id = ?")) {
            statement.setString(1, airship.getId().toString());
            ResultSet resultSet = statement.executeQuery();

            boolean result = resultSet.next();
            Assert.assertFalse(result);

            connection.rollback();
        }
    }

    @Test
    public void getAll() throws SQLException {
        connection.setAutoCommit(false);

        List<Airship> expected = new ArrayList<>(10);

        for (int i = 0; i < expected.size(); i++) {
            Airship airship = GeneratorSQL.getRandomAirship();
            expected.add(airship);
            airshipDAO.create(connection, airship);
        }

        List<Airship> actual = airshipDAO.getAll(connection); // test getAll
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());

        connection.rollback();
    }
}
