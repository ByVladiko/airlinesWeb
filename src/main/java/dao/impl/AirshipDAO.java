package dao.impl;

import dao.api.DAO;
import model.Airship;
import repository.DatabaseConnectivityProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class AirshipDAO implements DAO<Airship> {

    public AirshipDAO() {}

    private static final String CREATE_AIRSHIP = "INSERT INTO airship VALUES(?, ?, ?)";
    private static final String SELECT_AIRSHIP_BY_ID = "SELECT * FROM airship WHERE id = ?";
    private static final String UPDATE_AIRSHIP = "UPDATE airship SET model = ?, number_of_seat = ? WHERE id = ?";
    private static final String DELETE_AIRSHIP_BY_ID = "DELETE FROM airship WHERE id = ?";
    private static final String SELECT_ALL_AIRSHIPS = "SELECT * FROM airship";

    @Override
    public void create(Airship airship) {
        try (Connection connection = DatabaseConnectivityProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_AIRSHIP)) {
            statement.setString(1, airship.getId().toString());
            statement.setString(2, airship.getModel());
            statement.setInt(3, airship.getNumberOfSeat());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Airship getById(String id) {
        try (Connection connection = DatabaseConnectivityProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_AIRSHIP_BY_ID)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Airship(UUID.fromString(resultSet.getString("id")),
                        resultSet.getString("model"),
                        resultSet.getInt("number_of_seat"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Airship airship) {
        try (Connection connection = DatabaseConnectivityProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_AIRSHIP)) {
            statement.setString(1, airship.getModel());
            statement.setInt(2, airship.getNumberOfSeat());
            statement.setString(3, airship.getId().toString());
            if (statement.executeUpdate() == 0) {
                create(airship);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Airship airship) {
        try (Connection connection = DatabaseConnectivityProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_AIRSHIP_BY_ID)) {
            statement.setString(1, airship.getId().toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Airship> getAll() {
        try (Connection connection = DatabaseConnectivityProvider.getConnection();
             Statement statement = connection.createStatement()) {
            List<Airship> airships = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_AIRSHIPS);
            while (resultSet.next()) {
                airships.add(new Airship(UUID.fromString(resultSet.getString("id")),
                        resultSet.getString("model"),
                        resultSet.getInt("number_of_seat")));
            }
            return airships;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
