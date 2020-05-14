package dao.impl;

import dao.api.DAO;
import model.*;
import repository.DatabaseConnectivityProvider;
import util.DateConverter;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TicketDAO implements DAO<Ticket> {

    public TicketDAO() {
    }

    private static final String CREATE_TICKET = "INSERT INTO ticket VALUES(?, ?, ?, ?, ?)";
    private static final String GET_TICKET_BY_ID = "SELECT \n" +
            "                    a.id,\n" +
            "                    a.category,\n" +
            "                    a.cost,\n" +
            "                    a.baggage,\n" +
            "                    a.status,\n" +
            "                    b.id as flight_id,\n" +
            "                    b.date_of_departure,\n" +
            "                    b.date_of_arrival,\n" +
            "                    c.id as airship_id,\n" +
            "                    c.model as model,\n" +
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
            "status = ?, " +
            "client = ? " +
            "WHERE id = ?";
    private static final String DELETE_TICKET_BY_ID = "DELETE FROM ticket WHERE id = ?";
    private static final String SELECT_ALL_TICKETS = "SELECT \n" +
            "                    a.id,\n" +
            "                    a.category,\n" +
            "                    a.cost,\n" +
            "                    a.baggage,\n" +
            "                    a.status,\n" +
            "                    b.id as flight_id,\n" +
            "                    b.date_of_departure,\n" +
            "                    b.date_of_arrival,\n" +
            "                    c.id as airship_id,\n" +
            "                    c.model as airship_model,\n" +
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
    public void create(Ticket ticket) {
        try (Connection connection = DatabaseConnectivityProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_TICKET)) {
            statement.setString(1, ticket.getId().toString());
            statement.setString(2, ticket.getFlight().getId().toString());
            statement.setInt(3, ticket.getCategory().getIndex());
            statement.setFloat(4, ticket.getCost());
            statement.setString(5, null);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Ticket getById(String id) {
        try (Connection connection = DatabaseConnectivityProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_TICKET_BY_ID)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Ticket(UUID.fromString(resultSet.getString("id")),
                        new Flight(UUID.fromString(resultSet.getString("flight_id")),
                                DateConverter.convert(resultSet.getString("date_of_departure")),
                                DateConverter.convert(resultSet.getString("date_of_arrival")),
                                new Airship(UUID.fromString(resultSet.getString("airship_id")),
                                        resultSet.getString("model"),
                                        resultSet.getInt("economy_category"),
                                        resultSet.getInt("business_category"),
                                        resultSet.getInt("premium_category")),
                                new Route(UUID.fromString(resultSet.getString("route_id")),
                                        resultSet.getString("start_point"),
                                        resultSet.getString("end_point"))),
                        Category.byOrdinal(resultSet.getInt("category")),
                        resultSet.getFloat("cost"),
                        resultSet.getFloat("baggage"),
                        Status.byOrdinal(resultSet.getInt("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(final Connection connection, Ticket ticket) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_TICKET)) {
            statement.setString(1, ticket.getFlight().getId().toString());
            statement.setInt(2, ticket.getCategory().getIndex());
            statement.setFloat(3, ticket.getCost());
            statement.setString(4, ticket.getId().toString());
            if (statement.executeUpdate() == 0) {
                create(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Ticket ticket) {
        try (Connection connection = DatabaseConnectivityProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_TICKET_BY_ID)) {
            statement.setString(1, ticket.getId().toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Ticket> getAll() {
        try (Connection connection = DatabaseConnectivityProvider.getConnection();
             Statement statement = connection.createStatement()) {
            List<Ticket> ticketList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_TICKETS);
            while (resultSet.next()) {
                ticketList.add(
                        new Ticket(UUID.fromString(resultSet.getString("ticket_id")),
                                new Flight(UUID.fromString(resultSet.getString("flight_id")),
                                        DateConverter.convert(resultSet.getString("date_of_departure")),
                                        DateConverter.convert(resultSet.getString("date_of_arrival")),
                                        new Airship(UUID.fromString(resultSet.getString("id")),
                                                resultSet.getString("model"),
                                                resultSet.getInt("economy_category"),
                                                resultSet.getInt("business_category"),
                                                resultSet.getInt("premium_category")),
                                        new Route(UUID.fromString(resultSet.getString("route_id")),
                                                resultSet.getString("start_point"),
                                                resultSet.getString("end_point"))),
                                Category.byOrdinal(resultSet.getInt("category")),
                                resultSet.getFloat("cost"),
                                resultSet.getFloat("baggage"),
                                Status.byOrdinal(resultSet.getInt("status"))));
            }
            return ticketList;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
