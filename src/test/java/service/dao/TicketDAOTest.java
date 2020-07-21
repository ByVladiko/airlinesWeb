package service.dao;

import model.Category;
import model.Status;
import model.Ticket;
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

public class TicketDAOTest extends SQLTestOperations {

    @Test
    public void testCreate() throws SQLException {

        Ticket expected = createTicket();

        Ticket actual = ticketDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void testCreateException() throws SQLException {
        Ticket test = createTicket();

        Ticket ticket = GeneratorSQL.getRandomTicket();
        ticket.setId(test.getId());

        Assert.assertThrows(SQLException.class, () -> ticketDAO.create(connection, ticket));

        connection.rollback();
    }

    @Test
    public void testGetById() throws SQLException {

        Ticket expected = createTicket();

        Ticket actual = ticketDAO.getById(connection, expected.getId().toString());
        Assert.assertEquals(expected, actual);

        connection.rollback();
    }

    @Test
    public void testGetByIdException() {
        Assert.assertThrows(SQLException.class, () -> ticketDAO.getById(connection, UUID.randomUUID().toString()));
    }

    @Test
    public void testUpdate() throws SQLException {

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
    public void testUpdateException() throws SQLException {
        Ticket test = createTicket();

        test.setId(UUID.randomUUID());

        Assert.assertThrows(SQLException.class, () -> ticketDAO.update(connection, test));

        connection.rollback();
    }

    @Test
    public void testDelete() throws SQLException {

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
    public void testDeleteException() {
        Assert.assertThrows(SQLException.class, () -> ticketDAO.delete(connection, GeneratorSQL.getRandomTicket()));
    }

    @Test
    public void testGetAll() throws SQLException {
        int initialCapacity = 10;
        List<Ticket> expected = new ArrayList<>(initialCapacity);

        for (int i = 0; i < initialCapacity; i++) {
            Ticket ticket = createTicket();
            expected.add(ticket);
        }

        List<Ticket> actual = ticketDAO.getAll(connection);
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());

        connection.rollback();
    }
}
