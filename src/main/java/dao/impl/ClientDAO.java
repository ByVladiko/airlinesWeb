package dao.impl;

import dao.api.DAO;
import model.*;
import util.DateConverter;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ClientDAO implements DAO<Client> {

    public ClientDAO() {
    }

    private static Connection connection;

    private static final String CREATE_CLIENT = "INSERT INTO client VALUES(?, ?, ?, ?, ?)";
    private static final String GET_CLIENT_BY_ID = "SELECT  \n" +
            "                                *  \n" +
            "                        FROM   \n" +
            "                                client a  \n" +
            "                        LEFT JOIN  \n" +
            "                                (SELECT  \n" +
            "                                    b.id as ticket_id,  \n" +
            "                                    b.client,  \n" +
            "                                    b.category,   \n" +
            "                                    b.cost, \n" +
            "                                    b.baggage, \n" +
            "                                    b.status,  \n" +
            "                                    c.id as flight_id,   \n" +
            "                                    c.date_of_departure,   \n" +
            "                                    c.date_of_arrival, \n" +
            "                                    d.id as airship_id, \n" +
            "                                    d.model, \n" +
            "                                    d.economy_category, \n" +
            "                                    d.business_category, \n" +
            "                                    d.premium_category, \n" +
            "                                    e.id as route_id, \n" +
            "                                    e.start_point, \n" +
            "                                    e.end_point \n" +
            "                                FROM  \n" +
            "                                    ticket b  \n" +
            "                                INNER JOIN  \n" +
            "                                    flight c  \n" +
            "                                ON  \n" +
            "                                    b.flight = c.id \n" +
            "                                INNER JOIN \n" +
            "                                    airship d  \n" +
            "                                ON \n" +
            "                                    c.airship = d.id \n" +
            "                                INNER JOIN \n" +
            "                                    route e \n" +
            "                                ON \n" +
            "                                    c.route = e.id) f \n" +
            "                                ON   \n" +
            "                                    f.client = a.id\n" +
            "                                WHERE \n" +
            "                                    a.id = ?  \n" +
            "                                ORDER BY a.id";

    private static final String UPDATE_CLIENT = "UPDATE client " +
            "SET first_name = ?, " +
            "middle_name = ?, " +
            "last_name = ?, " +
            "bill = ? " +
            "WHERE id = ?";
    private static final String DELETE_CLIENT_BY_ID = "DELETE FROM client WHERE id = ?";
    private static final String SELECT_ALL_CLIENTS = "SELECT \n" +
            "                                * \n" +
            "                        FROM  \n" +
            "                                client a \n" +
            "                        LEFT JOIN \n" +
            "                                (SELECT \n" +
            "                                    b.id as ticket_id, \n" +
            "                                    b.client, \n" +
            "                                    b.category,  \n" +
            "                                    b.cost,\n" +
            "                                    b.baggage,\n" +
            "                                    b.status, \n" +
            "                                    c.id as flight_id,  \n" +
            "                                    c.date_of_departure,  \n" +
            "                                    c.date_of_arrival,\n" +
            "                                    c.airship,\n" +
            "                                    c.route,\n" +
            "                                    d.id as airship_id,\n" +
            "                                    d.model,\n" +
            "                                    d.economy_category,\n" +
            "                                    d.business_category,\n" +
            "                                    d.premium_category,\n" +
            "                                    f.id as route_id,\n" +
            "                                    f.start_point,\n" +
            "                                    f.end_point\n" +
            "                                FROM \n" +
            "                                    ticket b \n" +
            "                                INNER JOIN \n" +
            "                                    flight c \n" +
            "                                ON \n" +
            "                                    b.flight = c.id\n" +
            "                                INNER JOIN\n" +
            "                                    airship d \n" +
            "                                ON\n" +
            "                                    c.airship = d.id\n" +
            "                                INNER JOIN\n" +
            "                                    route f\n" +
            "                                ON\n" +
            "                                    c.route = f.id) g\n" +
            "                                ON  \n" +
            "                                    g.client = a.id \n" +
            "                                ORDER BY a.id";

    @Override
    public void create(final Connection connection, Client client) {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_CLIENT)) {
            statement.setString(1, client.getId().toString());
            statement.setString(2, client.getFirstName());
            statement.setString(3, client.getMiddleName());
            statement.setString(4, client.getLastName());
            statement.setFloat(5, client.getBill());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Client getById(Connection connection, String id) {
        try (PreparedStatement statement = connection.prepareStatement(GET_CLIENT_BY_ID)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Client client = new Client(UUID.fromString(resultSet.getString("id")),
                        resultSet.getString("first_name"),
                        resultSet.getString("middle_name"),
                        resultSet.getString("last_name"),
                        resultSet.getFloat("bill"));
                if (resultSet.getString("ticket_id") != null) {
                    ArrayList<Ticket> listTickets = new ArrayList<>();
                    listTickets.add(createTicket(resultSet));
                    while (resultSet.next()) {
                        listTickets.add(createTicket(resultSet));
                    }
                    client.setTickets(listTickets);
                }
                return client;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(final Connection connection, Client client) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CLIENT)) {
            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getMiddleName());
            statement.setString(3, client.getLastName());
            statement.setFloat(4, client.getBill());
            statement.setString(5, client.getId().toString());
            if (statement.executeUpdate() == 0) {
                create(connection, client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(final Connection connection, Client client) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CLIENT_BY_ID)) {
            statement.setString(1, client.getId().toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Client> getAll(final Connection connection) {
        try (Statement statement = connection.createStatement()) {
            List<Client> clientList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_CLIENTS);
            while (resultSet.next()) {
                Client client = new Client(UUID.fromString(resultSet.getString("id")),
                        resultSet.getString("first_name"),
                        resultSet.getString("middle_name"),
                        resultSet.getString("last_name"),
                        resultSet.getFloat("bill"));
                if (resultSet.getString("ticket_id") != null) {
                    ArrayList<Ticket> listTickets = new ArrayList<>();
                    listTickets.add(createTicket(resultSet));
                    while (resultSet.next()) {
                        if (client.getId().toString().equals(resultSet.getString("id"))) {
                            listTickets.add(createTicket(resultSet));
                        } else {
                            resultSet.previous();
                            break;
                        }
                    }
                    client.setTickets(listTickets);
                }
                clientList.add(client);
            }
            return clientList;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private Ticket createTicket(ResultSet resultSet) throws SQLException {
        return new Ticket(UUID.fromString(resultSet.getString("ticket_id")),
                new Flight(UUID.fromString(resultSet.getString("flight_id")),
                        DateConverter.convert(resultSet.getString("date_of_departure")),
                        DateConverter.convert(resultSet.getString("date_of_arrival")),
                        new Airship(UUID.fromString(resultSet.getString("airship_id,")),
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

}
