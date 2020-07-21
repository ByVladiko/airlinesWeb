package service.dao;

import model.Airship;
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

public class AirshipDAOTest extends SQLTestOperations {

    @Test
    public void testCreate() throws SQLException {
        Airship expected = createAirship();

        Airship actual = airshipDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void testCreateException() throws SQLException {
        Airship test = createAirship();

        Airship airship = GeneratorSQL.getRandomAirship();
        airship.setId(test.getId());

        Assert.assertThrows(SQLException.class, () -> airshipDAO.create(connection, airship));

        connection.rollback();
    }

    @Test
    public void testGetById() throws SQLException {
        Airship expected = createAirship();

        Airship actual = airshipDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void testGetByIdException() {
        Assert.assertThrows(SQLException.class, () -> airshipDAO.getById(connection, UUID.randomUUID().toString()));
    }

    @Test
    public void testUpdate() throws SQLException {
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
    public void testUpdateException() throws SQLException {
        Airship test = createAirship();

        test.setId(UUID.randomUUID());

        Assert.assertThrows(SQLException.class, () -> airshipDAO.update(connection, test));

        connection.rollback();
    }

    @Test
    public void testDelete() throws SQLException {
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
    public void testDeleteException() {
        Assert.assertThrows(SQLException.class, () -> airshipDAO.delete(connection, GeneratorSQL.getRandomAirship()));
    }

    @Test
    public void testGetAll() throws SQLException {
        int initialCapacity = 10;
        List<Airship> expected = new ArrayList<>(initialCapacity);

        for (int i = 0; i < initialCapacity; i++) {
            Airship airship = createAirship();
            expected.add(airship);
        }

        List<Airship> actual = airshipDAO.getAll(connection);
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());

        connection.rollback();
    }
}
