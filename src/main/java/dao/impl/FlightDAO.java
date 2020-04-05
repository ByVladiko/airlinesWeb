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

    private static String createFlight = "INSERT INTO flight VALUES(?, ?, ?, ?, ?)";
    private static String getByIdFlight = "SELECT\n" +
            "        a.id as flight_id,\n" +
            "        a.date_of_departure as date_departure,\n" +
            "        a.date_of_arrival as date_arrival,\n" +
            "        b.id as airship_id,\n" +
            "        b.model as airship_model,\n" +
            "        b.number_of_seat as airship_num,\n" +
            "        c.id as route_id,\n" +
            "        c.start_point as route_start,\n" +
            "        c.end_point as route_end\n" +
            "FROM\n" +
            "        flight a\n" +
            "INNER JOIN \n" +
            "        airship b\n" +
            "ON\n" +
            "        a.airship = b.id\n" +
            "INNER JOIN\n" +
            "        route c\n" +
            "ON\n" +
            "        a.route = c.id\n" +
            "WHERE\n" +
            "        a.id = ?";
    private static String updateFlight = "UPDATE flight SET "
            + "date_of_departure = ?, "
            + "date_of_arrival = ?, "
            + "airship = ?, "
            + "route = ? "
            + "WHERE id = ?";
    private static String deleteFlight = "DELETE FROM flight WHERE id = ?";
    private static String getAllFlight = "SELECT\n" +
            "        a.id as flight_id,\n" +
            "        a.date_of_departure as date_departure,\n" +
            "        a.date_of_arrival as date_arrival,\n" +
            "        b.id as airship_id,\n" +
            "        b.model as airship_model,\n" +
            "        b.number_of_seat as airship_num,\n" +
            "        c.id as route_id,\n" +
            "        c.start_point as route_start,\n" +
            "        c.end_point as route_end\n" +
            "FROM\n" +
            "        flight a\n" +
            "INNER JOIN \n" +
            "        airship b\n" +
            "ON\n" +
            "        a.airship = b.id\n" +
            "INNER JOIN\n" +
            "        route c\n" +
            "ON\n" +
            "        a.route = c.id";

    @Override
    public void create(Flight flight) {
        try (Connection connection = DatabaseConnectivityProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(createFlight)) {
            statement.setString(1, flight.getId().toString());
            statement.setString(2, DateConverter.convert(flight.getDateOfDeparture()));
            statement.setString(3, DateConverter.convert(flight.getDateOfArrival()));
            statement.setString(4, flight.getAirship().getId().toString());
            statement.setString(5, flight.getRoute().getId().toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Flight getById(String id) {
        try (Connection connection = DatabaseConnectivityProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(getByIdFlight)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Flight(UUID.fromString(resultSet.getString("flight_id")),
                        DateConverter.convert(resultSet.getString("date_departure")),
                        DateConverter.convert(resultSet.getString("date_arrival")),
                            new Airship(UUID.fromString(resultSet.getString("airship_id")),
                                    resultSet.getString("airship_model"),
                                    resultSet.getInt("airship_num")),
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
    public void update(Flight flight) {
        try (Connection connection = DatabaseConnectivityProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateFlight)) {
            statement.setString(1, DateConverter.convert(flight.getDateOfDeparture()));
            statement.setString(2, DateConverter.convert(flight.getDateOfArrival()));
            statement.setString(3, flight.getAirship().getId().toString());
            statement.setString(4, flight.getRoute().getId().toString());
            statement.setString(5, flight.getId().toString());
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
             PreparedStatement statement = connection.prepareStatement(deleteFlight)) {
            statement.setString(1, flight.getId().toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Flight> getAll() {
        try(Connection connection = DatabaseConnectivityProvider.getConnection();
            Statement statement = connection.createStatement())
        {
            List<Flight> flightList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(getAllFlight);
            while (resultSet.next()) {
                flightList.add(
                        new Flight(UUID.fromString(resultSet.getString("flight_id")),
                        DateConverter.convert(resultSet.getString("date_departure")),
                        DateConverter.convert(resultSet.getString("date_arrival")),
                            new Airship(UUID.fromString(resultSet.getString("airship_id")),
                                    resultSet.getString("airship_model"),
                                    resultSet.getInt("airship_num")),
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
