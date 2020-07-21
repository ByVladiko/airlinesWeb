package dao.impl;

import dao.api.DAO;
import model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static util.EntityFactoryDAO.createTicket;

public class TicketDAO implements DAO<Ticket> {

    public TicketDAO() {
    }

    private static final Logger logger = LoggerFactory.getLogger(RouteDAO.class);

    private static final String CREATE_TICKET = "INSERT INTO ticket VALUES(?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_TICKET_BY_ID = "SELECT \n" +
            "                    a.id as ticket_id,\n" +
            "                    a.category,\n" +
            "                    a.cost,\n" +
            "                    a.baggage,\n" +
            "                    a.status,\n" +
            "                    b.id as flight_id,\n" +
            "                    b.date_of_departure,\n" +
            "                    b.date_of_arrival,\n" +
            "                    c.id as airship_id,\n" +
            "                    c.model,\n" +
            "                    c.business_category,\n" +
            "                    c.economy_category,\n" +
            "                    c.premium_category,\n" +
            "                    d.id as route_id,\n" +
            "                    d.start_point,\n" +
            "                    d.end_point\n" +
            "            FROM\n" +
            "                    ticket a\n" +
            "            INNER JOIN\n" +
            "                    flight b\n" +
            "            ON\n" +
            "                    a.flight = b.id\n" +
            "            INNER JOIN \n" +
            "                    airship c\n" +
            "            ON\n" +
            "                    b.airship = c.id\n" +
            "            INNER JOIN\n" +
            "                    route d\n" +
            "            ON\n" +
            "                    b.route = d.id\n" +
            "            WHERE\n" +
            "                    a.id = ?";
    private static final String UPDATE_TICKET = "UPDATE ticket SET " +
            "flight = ?, " +
            "category = ?, " +
            "cost = ?, " +
            "baggage = ?, " +
            "status = ? " +
            "WHERE id = ?";
    private static final String DELETE_TICKET_BY_ID = "DELETE FROM ticket WHERE id = ?";
    private static final String SELECT_ALL_TICKETS = "SELECT \n" +
            "                    a.id as ticket_id,\n" +
            "                    a.category,\n" +
            "                    a.cost,\n" +
            "                    a.baggage,\n" +
            "                    a.status,\n" +
            "                    b.id as flight_id,\n" +
            "                    b.date_of_departure,\n" +
            "                    b.date_of_arrival,\n" +
            "                    c.id as airship_id,\n" +
            "                    c.model,\n" +
            "                    c.business_category,\n" +
            "                    c.economy_category,\n" +
            "                    c.premium_category,\n" +
            "                    d.id as route_id,\n" +
            "                    d.start_point,\n" +
            "                    d.end_point\n" +
            "            FROM\n" +
            "                    ticket a\n" +
            "            INNER JOIN\n" +
            "                    flight b\n" +
            "            ON\n" +
            "                    a.flight = b.id\n" +
            "            INNER JOIN \n" +
            "                    airship c\n" +
            "            ON\n" +
            "                    b.airship = c.id\n" +
            "            INNER JOIN\n" +
            "                    route d\n" +
            "            ON\n" +
            "                    b.route = d.id\n";

    @Override
    public void create(final Connection connection, Ticket ticket) throws SQLException {
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement(CREATE_TICKET)) {
            statement.setString(1, ticket.getId().toString());
            statement.setString(2, ticket.getFlight().getId().toString());
            statement.setInt(3, ticket.getCategory().getIndex());
            statement.setFloat(4, ticket.getCost());
            statement.setFloat(5, ticket.getBaggage());
            statement.setInt(6, ticket.getStatus().getIndex());
            statement.setString(7, null);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        if (result == 0) {
            throw new SQLException("Record has not been inserted");
        }
    }

    @Override
    public Ticket getById(final Connection connection, String id) throws SQLException {
        Ticket ticket = null;
        try (PreparedStatement statement = connection.prepareStatement(GET_TICKET_BY_ID)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                ticket = createTicket(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        if (ticket == null) {
            throw new SQLException("Record has not been inserted");
        }
        return ticket;
    }

    @Override
    public void update(final Connection connection, Ticket ticket) throws SQLException {
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_TICKET)) {
            statement.setString(1, ticket.getFlight().getId().toString());
            statement.setInt(2, ticket.getCategory().getIndex());
            statement.setFloat(3, ticket.getCost());
            statement.setFloat(4, ticket.getBaggage());
            statement.setFloat(5, ticket.getStatus().getIndex());
            statement.setString(6, ticket.getId().toString());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        if (result == 0) {
            throw new SQLException("No one record have been updated");
        }
    }

    @Override
    public void delete(final Connection connection, Ticket ticket) throws SQLException {
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement(DELETE_TICKET_BY_ID)) {
            statement.setString(1, ticket.getId().toString());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        if (result == 0) {
            throw new SQLException("No one record has been deleted");
        }
    }

    @Override
    public List<Ticket> getAll(final Connection connection) {
        try (Statement statement = connection.createStatement()) {
            List<Ticket> ticketList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_TICKETS);
            while (resultSet.next()) {
                ticketList.add(createTicket(resultSet));
            }
            return ticketList;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
