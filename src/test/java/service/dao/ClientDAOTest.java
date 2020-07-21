package service.dao;

import model.Client;
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

public class ClientDAOTest extends SQLTestOperations {

    @Test
    public void testCreate() throws SQLException {

        Client expected = createClient();

        Client actual = clientDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void testCreateException() throws SQLException {
        Client test = createClient();

        Client client = GeneratorSQL.getRandomClient();
        client.setId(test.getId());

        Assert.assertThrows(SQLException.class, () -> clientDAO.create(connection, client));

        connection.rollback();
    }

    @Test
    public void testGetById() throws SQLException {

        Client expected = createClient();

        Client actual = clientDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void testGetByIdException() {
        Assert.assertThrows(SQLException.class, () -> clientDAO.getById(connection, UUID.randomUUID().toString()));
    }

    @Test
    public void testUpdate() throws SQLException {

        Client expected = createClient();

        expected.setFirstName(GeneratorSQL.getRandomString());
        expected.setMiddleName(GeneratorSQL.getRandomString());
        expected.setLastName(GeneratorSQL.getRandomString());
        expected.setBill(GeneratorSQL.getRandomFloat(1000, 50000));

        clientDAO.update(connection, expected);

        Client actual = clientDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void testUpdateException() throws SQLException {
        Client test = createClient();

        test.setId(UUID.randomUUID());

        Assert.assertThrows(SQLException.class, () -> clientDAO.update(connection, test));

        connection.rollback();
    }

    @Test
    public void testDelete() throws SQLException {

        Client client = createClient();

        clientDAO.delete(connection, client);

        try (PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM client WHERE id = ?")) {
            statement.setString(1, client.getId().toString());
            ResultSet resultSet = statement.executeQuery();

            boolean result = resultSet.next();
            Assert.assertFalse(result);

            connection.rollback();
        }
    }

    @Test
    public void testDeleteException() {
        Assert.assertThrows(SQLException.class, () -> clientDAO.delete(connection, GeneratorSQL.getRandomClient()));
    }

    @Test
    public void testGetAll() throws SQLException {
        int initialCapacity = 10;
        List<Client> expected = new ArrayList<>(initialCapacity);

        for (int i = 0; i < initialCapacity; i++) {
            Client client = createClient();
            expected.add(client);
        }

        List<Client> actual = clientDAO.getAll(connection);
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());

        connection.rollback();
    }
}
