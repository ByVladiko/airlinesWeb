package dao.impl;

import dao.api.DAO;
import model.Airship;
import model.Flight;
import model.Route;
import repository.DatabaseConnectivityProvider;
import util.DateConverter;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class FlightDAO implements DAO<Flight> {

    public FlightDAO() {
    }

    private static final String CREATE_FLIGHT = "INSERT INTO flight VALUES(?, ?, ?, ?)";
    private static final String GET_FLIGHT_BY_ID = "SELECT \n" +
            "                    a.id as flight_id, \n" +
            "                    a.date_of_departure, \n" +
            "                    a.date_of_arrival, \n" +
            "                    b.id as airship_id, \n" +
            "                    b.model, \n" +
            "                    b.economy_category,\n" +
            "                    b.business_category,\n" +
            "                    b.premium_category,\n" +
            "                    d.id as route_id, \n" +
            "                    d.start_point, \n" +
            "                    d.end_point\n" +
            "            FROM \n" +
            "                    flight a \n" +
            "            INNER JOIN  \n" +
            "                    airship b \n" +
            "            ON \n" +
            "                    a.airship = b.id \n" +
            "            INNER JOIN \n" +
            "                    flight_route c \n" +
            "            ON \n" +
            "                    a.id = c.id_flight\n" +
            "            INNER JOIN\n" +
            "                    route d\n" +
            "            ON\n" +
            "                    c.id_route = d.id\n" +
            "            WHERE\n" +
            "                    a.id = ?";
    private static final String UPDATE_FLIGHT = "UPDATE flight SET "
            + "date_of_departure = ?, "
            + "date_of_arrival = ?, "
            + "airship = ?, "
            + "WHERE id = ?";
    private static final String DELETE_FLIGHT = "DELETE FROM flight WHERE id = ?";
    private static final String SELECT_ALL_FLIGHTS = "SELECT \n" +
            "                    a.id as flight_id, \n" +
            "                    a.date_of_departure, \n" +
            "                    a.date_of_arrival, \n" +
            "                    b.id as airship_id, \n" +
            "                    b.model, \n" +
            "                    b.economy_category,\n" +
            "                    b.business_category,\n" +
            "                    b.premium_category,\n" +
            "                    d.id as route_id, \n" +
            "                    d.start_point, \n" +
            "                    d.end_point\n" +
            "            FROM \n" +
            "                    flight a \n" +
            "            INNER JOIN  \n" +
            "                    airship b \n" +
            "            ON \n" +
            "                    a.airship = b.id \n" +
            "            INNER JOIN \n" +
            "                    flight_route c \n" +
            "            ON \n" +
            "                    a.id = c.id_flight\n" +
            "            INNER JOIN\n" +
            "                    route d\n" +
            "            ON\n" +
            "                    c.id_route = d.id";

    @Override
    public void create(Flight flight) {
        try (Connection connection = DatabaseConnectivityProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_FLIGHT);) {
            statement.setString(1, flight.getId().toString());
            statement.setString(2, DateConverter.convert(flight.getDateOfDeparture()));
            statement.setString(3, DateConverter.convert(flight.getDateOfArrival()));
            statement.setString(4, flight.getAirship().getId().toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Flight getById(String id) {
        try (Connection connection = DatabaseConnectivityProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_FLIGHT_BY_ID)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Flight(UUID.fromString(resultSet.getString("flight_id")),
                        DateConverter.convert(resultSet.getString("date_departure")),
                        DateConverter.convert(resultSet.getString("date_arrival")),
                        new Airship(UUID.fromString(resultSet.getString("id")),
                                resultSet.getString("model"),
                                resultSet.getInt("economy_category"),
                                resultSet.getInt("business_category"),
                                resultSet.getInt("premium_category")),
                        new Route(UUID.fromString(resultSet.getString("route_id")),
                                resultSet.getString("route_start"),
                                resultSet.getString("route_end")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(final Connection connection, Flight flight) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_FLIGHT)) {
            statement.setString(1, DateConverter.convert(flight.getDateOfDeparture()));
            statement.setString(2, DateConverter.convert(flight.getDateOfArrival()));
            statement.setString(3, flight.getAirship().getId().toString());
            statement.setString(4, flight.getId().toString());
            if (statement.executeUpdate() == 0) {
                create(flight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Flight flight) {
        try (Connection connection = DatabaseConnectivityProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_FLIGHT)) {
            statement.setString(1, flight.getId().toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Flight> getAll() {
        try (Connection connection = DatabaseConnectivityProvider.getConnection();
             Statement statement = connection.createStatement()) {
            List<Flight> flightList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_FLIGHTS);
            while (resultSet.next()) {
                flightList.add(
                        new Flight(UUID.fromString(resultSet.getString("flight_id")),
                                DateConverter.convert(resultSet.getString("date_departure")),
                                DateConverter.convert(resultSet.getString("date_arrival")),
                                new Airship(UUID.fromString(resultSet.getString("id")),
                                        resultSet.getString("model"),
                                        resultSet.getInt("economy_category"),
                                        resultSet.getInt("business_category"),
                                        resultSet.getInt("premium_category")),
                                new Route(UUID.fromString(resultSet.getString("route_id")),
                                        resultSet.getString("route_start"),
                                        resultSet.getString("route_end"))));
            }
            return flightList;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
