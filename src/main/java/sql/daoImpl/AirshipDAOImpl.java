package sql.daoImpl;

import dao.DAO;
import model.Airship;
import sql.ConnectToDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class AirshipDAOImpl implements DAO<Airship> {

    private static AirshipDAOImpl airshipDAO;
    private Connection connection = ConnectToDB.getConnection();

    private AirshipDAOImpl() {
    }

    static AirshipDAOImpl getInstance() {
        if (airshipDAO == null) {
            airshipDAO = new AirshipDAOImpl();
        }
        return airshipDAO;
    }

    @Override
    public void create(Airship airship) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO airship " +
                        "VALUES(?, ?, ?)")) {
            statement.setObject(1, airship.getId().toString());
            statement.setObject(2, airship.getModel());
            statement.setObject(3, airship.getNumberOfSeat());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Airship getById(int i) {
        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM airship WHERE id = ?");
            if (resultSet.first()) {
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
        try (PreparedStatement statement = this.connection.prepareStatement("UPDATE airship SET model = ? , "
                + "number_of_seat = ? "
                + "WHERE id = ?")) {
            statement.setObject(1, airship.getModel());
            statement.setObject(2, airship.getNumberOfSeat());
            statement.setObject(3, airship.getId().toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Airship airship) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROM airship WHERE id = ?")) {
            statement.setObject(1, airship.getId().toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Airship> getAll() {
        try (Statement statement = this.connection.createStatement()) {
            List<Airship> airships = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM airship");
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
