package dao.impl;

import dao.api.DAO;
import model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static util.EntityFactoryDAO.createClient;

public class ClientDAO implements DAO<Client> {

    public ClientDAO() {
    }

    private static final Logger logger = LoggerFactory.getLogger(ClientDAO.class);

    private static final String CREATE_CLIENT = "INSERT INTO client VALUES(?, ?, ?, ?, ?)";
    private static final String GET_CLIENT_BY_ID = "SELECT a.id AS client_id,\n" +
            "       a.first_name,\n" +
            "       a.middle_name,\n" +
            "       a.last_name,\n" +
            "       a.bill,\n" +
            "       f.ticket_id,\n" +
            "       f.client,\n" +
            "       f.category,\n" +
            "       f.cost,\n" +
            "       f.baggage,\n" +
            "       f.status,\n" +
            "       f.flight_id,\n" +
            "       f.date_of_departure,\n" +
            "       f.date_of_arrival,\n" +
            "       f.airship_id,\n" +
            "       f.model,\n" +
            "       f.economy_category,\n" +
            "       f.business_category,\n" +
            "       f.premium_category,\n" +
            "       f.route_id,\n" +
            "       f.start_point,\n" +
            "       f.end_point\n" +
            "  FROM client a\n" +
            "       LEFT JOIN\n" +
            "       (\n" +
            "           SELECT b.id AS ticket_id,\n" +
            "                  b.client,\n" +
            "                  b.category,\n" +
            "                  b.cost,\n" +
            "                  b.baggage,\n" +
            "                  b.status,\n" +
            "                  c.id AS flight_id,\n" +
            "                  c.date_of_departure,\n" +
            "                  c.date_of_arrival,\n" +
            "                  d.id AS airship_id,\n" +
            "                  d.model,\n" +
            "                  d.economy_category,\n" +
            "                  d.business_category,\n" +
            "                  d.premium_category,\n" +
            "                  e.id AS route_id,\n" +
            "                  e.start_point,\n" +
            "                  e.end_point\n" +
            "             FROM ticket b\n" +
            "                  INNER JOIN\n" +
            "                  flight c ON b.flight = c.id\n" +
            "                  INNER JOIN\n" +
            "                  airship d ON c.airship = d.id\n" +
            "                  INNER JOIN\n" +
            "                  route e ON c.route = e.id\n" +
            "       )\n" +
            "       f ON f.client = a.id\n" +
            " WHERE a.id = ?\n";

    private static final String UPDATE_CLIENT = "UPDATE client " +
            "SET first_name = ?, " +
            "middle_name = ?, " +
            "last_name = ?, " +
            "bill = ? " +
            "WHERE id = ?";
    private static final String DELETE_CLIENT_BY_ID = "DELETE FROM client WHERE id = ?";
    private static final String SELECT_ALL_CLIENTS = "SELECT a.id AS client_id,\n" +
            "       a.first_name,\n" +
            "       a.middle_name,\n" +
            "       a.last_name,\n" +
            "       a.bill,\n" +
            "       f.ticket_id,\n" +
            "       f.client,\n" +
            "       f.category,\n" +
            "       f.cost,\n" +
            "       f.baggage,\n" +
            "       f.status,\n" +
            "       f.flight_id,\n" +
            "       f.date_of_departure,\n" +
            "       f.date_of_arrival,\n" +
            "       f.airship_id,\n" +
            "       f.model,\n" +
            "       f.economy_category,\n" +
            "       f.business_category,\n" +
            "       f.premium_category,\n" +
            "       f.route_id,\n" +
            "       f.start_point,\n" +
            "       f.end_point\n" +
            "  FROM client a\n" +
            "       LEFT JOIN\n" +
            "       (\n" +
            "           SELECT b.id AS ticket_id,\n" +
            "                  b.client,\n" +
            "                  b.category,\n" +
            "                  b.cost,\n" +
            "                  b.baggage,\n" +
            "                  b.status,\n" +
            "                  c.id AS flight_id,\n" +
            "                  c.date_of_departure,\n" +
            "                  c.date_of_arrival,\n" +
            "                  d.id AS airship_id,\n" +
            "                  d.model,\n" +
            "                  d.economy_category,\n" +
            "                  d.business_category,\n" +
            "                  d.premium_category,\n" +
            "                  e.id AS route_id,\n" +
            "                  e.start_point,\n" +
            "                  e.end_point\n" +
            "             FROM ticket b\n" +
            "                  INNER JOIN\n" +
            "                  flight c ON b.flight = c.id\n" +
            "                  INNER JOIN\n" +
            "                  airship d ON c.airship = d.id\n" +
            "                  INNER JOIN\n" +
            "                  route e ON c.route = e.id\n" +
            "       )\n" +
            "       f ON f.client = a.id";

    @Override
    public void create(final Connection connection, Client client) throws SQLException {
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement(CREATE_CLIENT)) {
            statement.setString(1, client.getId().toString());
            statement.setString(2, client.getFirstName());
            statement.setString(3, client.getMiddleName());
            statement.setString(4, client.getLastName());
            statement.setFloat(5, client.getBill());
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
    public Client getById(final Connection connection, String id) {
        Client client = null;
        try (PreparedStatement statement = connection.prepareStatement(GET_CLIENT_BY_ID)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                client = createClient(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public void update(final Connection connection, Client client) throws SQLException {
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CLIENT)) {
            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getMiddleName());
            statement.setString(3, client.getLastName());
            statement.setFloat(4, client.getBill());
            statement.setString(5, client.getId().toString());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        if (result == 0) {
            throw new SQLException("No one record have been updated");
        } else if (result > 1) {
            throw new SQLException("More than one record has been updated");
        }
    }

    @Override
    public void delete(final Connection connection, Client client) throws SQLException {
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CLIENT_BY_ID)) {
            statement.setString(1, client.getId().toString());
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
    public List<Client> getAll(final Connection connection) {
        try (Statement statement = connection.createStatement()) {
            List<Client> clientList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_CLIENTS);
            while (resultSet.next()) {
                clientList.add(createClient(resultSet));
            }
            return clientList;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
