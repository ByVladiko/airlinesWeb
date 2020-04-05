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

public class ClientDAO implements DAO<Client> {

    public ClientDAO() {
    }

    private static String createClient = "INSERT INTO client VALUES(?, ?, ?, ?)";
    private static String getByIdClient = "SELECT\n" +
            "        *\n" +
            "FROM \n" +
            "        client a\n" +
            "LEFT JOIN\n" +
            "        (SELECT\n" +
            "            b.id as ticket_id,\n" +
            "            b.client,\n" +
            "            b.category, \n" +
            "            b.cost,\n" +
            "            c.id as flight_id, \n" +
            "            c.date_of_departure, \n" +
            "            c.date_of_arrival, \n" +
            "            d.id as airship_id, \n" +
            "            d.model, \n" +
            "            d.number_of_seat, \n" +
            "            e.id as route_id, \n" +
            "            e.start_point, \n" +
            "            e.end_point\n" +
            "        FROM\n" +
            "            ticket b\n" +
            "        INNER JOIN\n" +
            "            flight c\n" +
            "        ON\n" +
            "            b.flight = c.id\n" +
            "        INNER JOIN\n" +
            "            airship d\n" +
            "        ON\n" +
            "                c.airship = d.id\n" +
            "        INNER JOIN\n" +
            "                route e\n" +
            "        ON\n" +
            "                c.route = e.id) f\n" +
            "        ON \n" +
            "            f.client = a.id\n" +
            "        WHERE a.id = ?\n" +
            "        ORDER BY a.id";
    private static String updateClient = "UPDATE client SET first_name = ?, middle_name = ?, last_name = ? WHERE id = ?";
    private static String deleteClient = "DELETE FROM client WHERE id = ?";
    private static String getAllClient = "SELECT\n" +
            "        *\n" +
            "FROM \n" +
            "        client a\n" +
            "LEFT JOIN\n" +
            "        (SELECT\n" +
            "            b.id as ticket_id,\n" +
            "            b.client,\n" +
            "            b.category, \n" +
            "            b.cost,\n" +
            "            c.id as flight_id, \n" +
            "            c.date_of_departure, \n" +
            "            c.date_of_arrival, \n" +
            "            d.id as airship_id, \n" +
            "            d.model, \n" +
            "            d.number_of_seat, \n" +
            "            e.id as route_id, \n" +
            "            e.start_point, \n" +
            "            e.end_point\n" +
            "        FROM\n" +
            "            ticket b\n" +
            "        INNER JOIN\n" +
            "            flight c\n" +
            "        ON\n" +
            "            b.flight = c.id\n" +
            "        INNER JOIN\n" +
            "            airship d\n" +
            "        ON\n" +
            "                c.airship = d.id\n" +
            "        INNER JOIN\n" +
            "                route e\n" +
            "        ON\n" +
            "                c.route = e.id) f\n" +
            "        ON \n" +
            "            f.client = a.id\n" +
            "        ORDER BY a.id";

    @Override
    public void create(Client client) {
        try (Connection connection = DatabaseConnectivityProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(createClient)) {
            statement.setString(1, client.getId().toString());
            statement.setString(2, client.getFirstName());
            statement.setString(3, client.getMiddleName());
            statement.setString(4, client.getLastName());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Client getById(String id) {
        try (Connection connection = DatabaseConnectivityProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(getByIdClient)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Client client = new Client(UUID.fromString(resultSet.getString("id")),
                        resultSet.getString("first_name"),
                        resultSet.getString("middle_name"),
                        resultSet.getString("last_name"));
                if (resultSet.getString("ticket_id") != null) {
                    ArrayList<Ticket> listTickets = new ArrayList<>();
                    listTickets.add(new Ticket(UUID.fromString(resultSet.getString("ticket_id")),
                            new Flight(UUID.fromString(resultSet.getString("flight_id")),
                                    DateConverter.convert(resultSet.getString("date_of_departure")),
                                    DateConverter.convert(resultSet.getString("date_of_arrival")),
                                    new Airship(UUID.fromString(resultSet.getString("airship_id")),
                                            resultSet.getString("model"),
                                            resultSet.getInt("number_of_seat")),
                                    new Route(UUID.fromString(resultSet.getString("route_id")),
                                            resultSet.getString("start_point"),
                                            resultSet.getString("end_point"))),
                            Category.byOrdinal(resultSet.getInt("category")),
                            resultSet.getFloat("cost")));
                    while (resultSet.next()) {
                        listTickets.add(new Ticket(UUID.fromString(resultSet.getString("ticket_id")),
                                new Flight(UUID.fromString(resultSet.getString("flight_id")),
                                        DateConverter.convert(resultSet.getString("date_departure")),
                                        DateConverter.convert(resultSet.getString("date_arrival")),
                                        new Airship(UUID.fromString(resultSet.getString("airship_id")),
                                                resultSet.getString("model"),
                                                resultSet.getInt("number_of_seat")),
                                        new Route(UUID.fromString(resultSet.getString("route_id")),
                                                resultSet.getString("start_point"),
                                                resultSet.getString("end_point"))),
                                Category.byOrdinal(resultSet.getInt("category")),
                                resultSet.getFloat("cost")));
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
    public void update(Client client) {
        try (Connection connection = DatabaseConnectivityProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateClient)) {
            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getMiddleName());
            statement.setString(3, client.getLastName());
            statement.setString(4, client.getId().toString());
            if (statement.executeUpdate() == 0) {
                create(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Client client) {
        try (Connection connection = DatabaseConnectivityProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteClient)) {
            statement.setString(1, client.getId().toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Client> getAll() {
        try (Connection connection = DatabaseConnectivityProvider.getConnection();
             Statement statement = connection.createStatement()) {
            List<Client> clientList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(getAllClient);
            while (resultSet.next()) {
                Client client = new Client(UUID.fromString(resultSet.getString("id")),
                        resultSet.getString("first_name"),
                        resultSet.getString("middle_name"),
                        resultSet.getString("last_name"));
                if (resultSet.getString("ticket_id") != null) {
                    ArrayList<Ticket> listTickets = new ArrayList<>();
                    listTickets.add(new Ticket(UUID.fromString(resultSet.getString("ticket_id")),
                            new Flight(UUID.fromString(resultSet.getString("flight_id")),
                                    DateConverter.convert(resultSet.getString("date_departure")),
                                    DateConverter.convert(resultSet.getString("date_arrival")),
                                    new Airship(UUID.fromString(resultSet.getString("airship_id")),
                                            resultSet.getString("model"),
                                            resultSet.getInt("number_of_seat")),
                                    new Route(UUID.fromString(resultSet.getString("route_id")),
                                            resultSet.getString("start_point"),
                                            resultSet.getString("end_point"))),
                            Category.byOrdinal(resultSet.getInt("category")),
                            resultSet.getFloat("cost")));
                    while (resultSet.next()) {
                        if (client.getId().toString().equals(resultSet.getString("id"))) {
                            listTickets.add(new Ticket(UUID.fromString(resultSet.getString("ticket_id")),
                                    new Flight(UUID.fromString(resultSet.getString("flight_id")),
                                            DateConverter.convert(resultSet.getString("date_departure")),
                                            DateConverter.convert(resultSet.getString("date_arrival")),
                                            new Airship(UUID.fromString(resultSet.getString("airship_id")),
                                                    resultSet.getString("model"),
                                                    resultSet.getInt("number_of_seat")),
                                            new Route(UUID.fromString(resultSet.getString("route_id")),
                                                    resultSet.getString("start_point"),
                                                    resultSet.getString("end_point"))),
                                    Category.byOrdinal(resultSet.getInt("category")),
                                    resultSet.getFloat("cost")));
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

}
