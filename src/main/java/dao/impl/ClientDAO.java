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
            int result = statement.executeUpdate();
            if (result == 0) {
                throw new SQLException("Record has not been inserted");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    public Client getById(final Connection connection, String id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(GET_CLIENT_BY_ID)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createClient(resultSet);
            } else {
                throw new SQLException("Can't get record by this id");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        throw new SQLException("Unsuccessful operation");
    }

    @Override
    public void update(final Connection connection, Client client) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CLIENT)) {
            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getMiddleName());
            statement.setString(3, client.getLastName());
            statement.setFloat(4, client.getBill());
            statement.setString(5, client.getId().toString());
            int result = statement.executeUpdate();
            if (result == 0) {
                throw new SQLException("No one record have been updated");
            } else if (result > 1) {
                throw new SQLException("More than one record has been updated");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    public void delete(final Connection connection, Client client) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CLIENT_BY_ID)) {
            statement.setString(1, client.getId().toString());
            int result = statement.executeUpdate();
            if (result == 0) {
                throw new SQLException("No one record has been deleted");
            } else if (result > 1) {
                throw new SQLException("More than one record has been deleted");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    public List<Client> getAll(final Connection connection) {
        try (Statement statement = connection.createStatement()) {
            List<Client> clientList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_CLIENTS);
            while (resultSet.next()) {
                Client client = createClient(resultSet);
                clientList.add(client);
            }
            return clientList;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
