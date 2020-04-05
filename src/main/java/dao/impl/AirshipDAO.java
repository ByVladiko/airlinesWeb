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

    private static String createAirship = "INSERT INTO airship VALUES(?, ?, ?)";
    private static String getByIdAirship = "SELECT * FROM airship WHERE id = ?";
    private static String updateAirship = "UPDATE airship SET model = ?, number_of_seat = ? WHERE id = ?";
    private static String deleteAirship = "DELETE FROM airship WHERE id = ?";
    private static String getAllAirship = "SELECT * FROM airship";

    @Override
    public void create(Airship airship) {
        try (Connection connection = DatabaseConnectivityProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(createAirship)) {
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
             PreparedStatement statement = connection.prepareStatement(getByIdAirship)) {
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
             PreparedStatement statement = connection.prepareStatement(updateAirship)) {
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
             PreparedStatement statement = connection.prepareStatement(deleteAirship)) {
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
            ResultSet resultSet = statement.executeQuery(getAllAirship);
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
