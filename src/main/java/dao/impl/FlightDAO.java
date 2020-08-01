package dao.impl;

import dao.api.DAO;
import model.Flight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.DateConverter;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static util.EntityFactoryDAO.createFlight;

public class FlightDAO implements DAO<Flight> {

    public FlightDAO() {
    }

    private static final Logger logger = LoggerFactory.getLogger(FlightDAO.class);

    private static final String CREATE_FLIGHT = "INSERT INTO flight VALUES(?, ?, ?, ?, ?)";
    private static final String GET_FLIGHT_BY_ID = "SELECT \n" +
            "                                            a.id as flight_id, \n" +
            "                                            a.date_of_departure, \n" +
            "                                            a.date_of_arrival, \n" +
            "                                            b.id as airship_id, \n" +
            "                                            b.model, \n" +
            "                                            b.economy_category,\n" +
            "                                            b.business_category,\n" +
            "                                            b.premium_category,\n" +
            "                                            c.id as route_id, \n" +
            "                                            c.start_point, \n" +
            "                                            c.end_point\n" +
            "                                    FROM \n" +
            "                                            flight a \n" +
            "                                    INNER JOIN  \n" +
            "                                            airship b \n" +
            "                                    ON \n" +
            "                                            a.airship = b.id \n" +
            "                                    INNER JOIN \n" +
            "                                           route c \n" +
            "                                    ON \n" +
            "                                            a.route = c.id \n" +
            "                        WHERE\n" +
            "                                a.id = ?";
    private static final String UPDATE_FLIGHT = "UPDATE flight SET "
            + "date_of_departure = ?, "
            + "date_of_arrival = ?, "
            + "airship = ?, "
            + "route = ? "
            + "WHERE id = ?";
    private static final String DELETE_FLIGHT = "DELETE FROM flight WHERE id = ?";
    private static final String SELECT_ALL_FLIGHTS = "SELECT \n" +
            "                                a.id as flight_id, \n" +
            "                                a.date_of_departure, \n" +
            "                                a.date_of_arrival, \n" +
            "                                b.id as airship_id, \n" +
            "                                b.model, \n" +
            "                                b.economy_category,\n" +
            "                                b.business_category,\n" +
            "                                b.premium_category,\n" +
            "                                c.id as route_id, \n" +
            "                                c.start_point, \n" +
            "                                c.end_point\n" +
            "                        FROM \n" +
            "                                flight a \n" +
            "                        INNER JOIN  \n" +
            "                                airship b \n" +
            "                        ON \n" +
            "                                a.airship = b.id \n" +
            "                        INNER JOIN \n" +
            "                               route c \n" +
            "                        ON \n" +
            "                                a.route = c.id";

    @Override
    public void create(final Connection connection, Flight flight) throws SQLException {
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement(CREATE_FLIGHT);) {
            statement.setString(1, flight.getId().toString());
            statement.setString(2, DateConverter.convert(flight.getDateOfDeparture()));
            statement.setString(3, DateConverter.convert(flight.getDateOfArrival()));
            statement.setString(4, flight.getAirship().getId().toString());
            statement.setString(5, flight.getRoute().getId().toString());
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
    public Flight getById(Connection connection, String id) {
        Flight flight = null;
        try (PreparedStatement statement = connection.prepareStatement(GET_FLIGHT_BY_ID)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                flight = createFlight(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return flight;
    }

    @Override
    public void update(final Connection connection, Flight flight) throws SQLException {
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_FLIGHT)) {
            statement.setString(1, DateConverter.convert(flight.getDateOfDeparture()));
            statement.setString(2, DateConverter.convert(flight.getDateOfArrival()));
            statement.setString(3, flight.getAirship().getId().toString());
            statement.setString(4, flight.getRoute().getId().toString());
            statement.setString(5, flight.getId().toString());
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
    public void delete(final Connection connection, Flight flight) throws SQLException {
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement(DELETE_FLIGHT)) {
            statement.setString(1, flight.getId().toString());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        if (result == 0) {
            throw new SQLException("No one record has been deleted");
        } else if (result > 1) {
            throw new SQLException("More than one record has been deleted");
        }
    }

    @Override
    public List<Flight> getAll(final Connection connection) {
        try (Statement statement = connection.createStatement()) {
            List<Flight> flightList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_FLIGHTS);
            while (resultSet.next()) {
                flightList.add(createFlight(resultSet));
            }
            return flightList;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
