package dao;

import model.Category;
import model.Flight;
import model.Status;
import model.Ticket;
import org.junit.Assert;
import org.junit.Test;
import util.GeneratorSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestTicketDAO extends TestDAO {

    @Test
    public void create() throws SQLException {
        connection.setAutoCommit(false);

        Ticket expected = createTicket();

        Ticket actual = ticketDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void getById() throws SQLException {
        connection.setAutoCommit(false);

        Ticket expected = createTicket();

        Ticket actual = ticketDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void update() throws SQLException {
        connection.setAutoCommit(false);

        Ticket expected = createTicket();

        expected.setStatus(Status.byOrdinal(GeneratorSQL.getRandomInt(1, 4)));
        expected.setBaggage(GeneratorSQL.getRandomFloat(1, 50));
        expected.setCategory(Category.byOrdinal(GeneratorSQL.getRandomInt(1, 4)));
        expected.setCost(GeneratorSQL.getRandomFloat(1000, 30000));
        ticketDAO.update(connection, expected);

        Ticket actual = ticketDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void delete() throws SQLException {
        connection.setAutoCommit(false);

        Ticket ticket = createTicket();

        ticketDAO.delete(connection, ticket);

        try (PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM ticket WHERE id = ?")) {
            statement.setString(1, ticket.getId().toString());
            ResultSet resultSet = statement.executeQuery();

            boolean result = resultSet.next();
            Assert.assertFalse(result);

            connection.rollback();
        }
    }

    @Test
    public void getAll() throws SQLException {
        connection.setAutoCommit(false);

        List<Ticket> expected = new ArrayList<>(10);

        for (int i = 0; i < expected.size(); i++) {
            Ticket ticket = createTicket();
            expected.add(ticket);
        }

        List<Flight> actual = flightDAO.getAll(connection);
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());

        connection.rollback();
    }

    private Ticket createTicket() {
        Ticket ticket = GeneratorSQL.getRandomTicket();
        airshipDAO.create(connection, ticket.getFlight().getAirship());
        routeDAO.create(connection, ticket.getFlight().getRoute());
        flightDAO.create(connection, ticket.getFlight());
        ticketDAO.create(connection, ticket);
        return ticket;
    }
}
