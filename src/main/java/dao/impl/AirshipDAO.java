package dao.impl;

import dao.api.DAO;
import model.Airship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static util.EntityFactoryDAO.createAirship;

public class AirshipDAO implements DAO<Airship> {

    private static final Logger logger = LoggerFactory.getLogger(AirshipDAO.class);

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
    public void create(final Connection connection, Airship airship) throws SQLException {
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement(CREATE_AIRSHIP)) {
            statement.setString(1, airship.getId().toString());
            statement.setString(2, airship.getModel());
            statement.setInt(3, airship.getEconomyCategory());
            statement.setInt(4, airship.getBusinessCategory());
            statement.setInt(5, airship.getPremiumCategory());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getSQLState(), e);
            e.printStackTrace();
        }
        if (result == 0) throw new SQLException("Record has not been inserted");
    }

    @Override
    public Airship getById(final Connection connection, String id) throws SQLException {
        Airship airship = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_AIRSHIP_BY_ID)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                airship = createAirship(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        if (airship == null) {
            throw new SQLException("Can't get record by this id");
        }
        return airship;
    }


    @Override
    public void update(final Connection connection, Airship airship) throws SQLException {
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_AIRSHIP)) {
            statement.setString(1, airship.getModel());
            statement.setInt(2, airship.getEconomyCategory());
            statement.setInt(3, airship.getBusinessCategory());
            statement.setInt(4, airship.getPremiumCategory());
            statement.setString(5, airship.getId().toString());
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
    public void delete(final Connection connection, Airship airship) throws SQLException {
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement(DELETE_AIRSHIP_BY_ID)) {
            statement.setString(1, airship.getId().toString());
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
    public List<Airship> getAll(final Connection connection) {
        try (Statement statement = connection.createStatement()) {
            List<Airship> airships = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_AIRSHIPS);
            while (resultSet.next()) {
                airships.add(createAirship(resultSet));
            }
            return airships;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
