package dao.impl;

import dao.api.DAO;
import model.Airship;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class AirshipDAO implements DAO<Airship> {

    public AirshipDAO() {
    }

    private static final String CREATE_AIRSHIP = "INSERT INTO airship VALUES(?, ?, ?, ?, ?)";
    private static final String SELECT_AIRSHIP_BY_ID = "SELECT * FROM airship WHERE id = ?";
    private static final String UPDATE_AIRSHIP = "UPDATE airship\n" +
            "SET " +
            "       model = ?,\n" +
            "       economy_category = ?,\n" +
            "       business_category = ?,\n" +
            "       premium_category = ?\n" +
            " WHERE id = ?";
    private static final String DELETE_AIRSHIP_BY_ID = "DELETE FROM airship WHERE id = ?";
    private static final String SELECT_ALL_AIRSHIPS = "SELECT * FROM airship";

    @Override
    public void create(final Connection connection, Airship airship) {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_AIRSHIP)) {
            statement.setString(1, airship.getId().toString());
            statement.setString(2, airship.getModel());
            statement.setInt(3, airship.getEconomyCategory());
            statement.setInt(4, airship.getBusinessCategory());
            statement.setInt(5, airship.getPremiumCategory());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Airship getById(Connection connection, String id) {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_AIRSHIP_BY_ID)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Airship(UUID.fromString(resultSet.getString("id")),
                        resultSet.getString("model"),
                        resultSet.getInt("economy_category"),
                        resultSet.getInt("business_category"),
                        resultSet.getInt("premium_category"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(final Connection connection, Airship airship) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_AIRSHIP)) {
            statement.setString(1, airship.getModel());
            statement.setInt(2, airship.getEconomyCategory());
            statement.setInt(3, airship.getBusinessCategory());
            statement.setInt(4, airship.getPremiumCategory());
            if (statement.executeUpdate() == 0) {
                create(connection, airship);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(final Connection connection, Airship airship) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_AIRSHIP_BY_ID)) {
            statement.setString(1, airship.getId().toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Airship> getAll(final Connection connection) {
        try (Statement statement = connection.createStatement()) {
            List<Airship> airships = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_AIRSHIPS);
            while (resultSet.next()) {
                airships.add(new Airship(UUID.fromString(resultSet.getString("id")),
                        resultSet.getString("model"),
                        resultSet.getInt("economy_category"),
                        resultSet.getInt("business_category"),
                        resultSet.getInt("premium_category")));
            }
            return airships;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
