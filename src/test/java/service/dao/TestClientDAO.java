package service.dao;

import model.Client;
import org.junit.Assert;
import org.junit.Test;
import service.MainTestOperations;
import util.GeneratorSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestClientDAO extends MainTestOperations {

    @Test
    public void create() throws SQLException {
        connection.setAutoCommit(false);

        Client expected = createClient();

        Client actual = clientDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void getById() throws SQLException {
        connection.setAutoCommit(false);

        Client expected = createClient();

        Client actual = clientDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void update() throws SQLException {
        connection.setAutoCommit(false);

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
    public void delete() throws SQLException {
        connection.setAutoCommit(false);

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
    public void getAll() throws SQLException {
        connection.setAutoCommit(false);

        List<Client> expected = new ArrayList<>(10);

        for (int i = 0; i < expected.size(); i++) {
            Client client = createClient();
            expected.add(client);
        }

        List<Client> actual = clientDAO.getAll(connection);
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());

        connection.rollback();
    }
}
