package dao;

import model.Client;
import model.Ticket;
import org.junit.Assert;
import org.junit.Test;
import util.GeneratorSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestClientDAO extends TestDAO {

    @Test
    public void create() throws SQLException {
        connection.setAutoCommit(false);

        Client expected = createClient();

        Ticket actual = ticketDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void getById() throws SQLException {
        connection.setAutoCommit(false);

        Client expected = createClient();

        Ticket actual = ticketDAO.getById(connection, expected.getId().toString());
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

        Ticket actual = ticketDAO.getById(connection, expected.getId().toString());
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

    private Client createClient() {
        Client client = GeneratorSQL.getRandomClient();
        clientDAO.create(connection, client);
        int countOfTickets = GeneratorSQL.getRandomInt(3, 10);
        List<Ticket> tickets = new ArrayList<>(countOfTickets);
        for (int i = 0; i < countOfTickets; i++) {
            Ticket ticket = GeneratorSQL.getRandomTicket();
            airshipDAO.create(connection, ticket.getFlight().getAirship());
            routeDAO.create(connection, ticket.getFlight().getRoute());
            flightDAO.create(connection, ticket.getFlight());
            ticketDAO.create(connection, ticket);
            tickets.add(ticket);
        }
        client.setTickets(tickets);
        clientDAO.update(connection, client);
        return client;
    }
}
