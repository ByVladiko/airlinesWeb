package sql.daoImpl;

import dao.DAO;
import model.Route;
import sql.ConnectToDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class RouteDAOImpl implements DAO<Route> {

    public RouteDAOImpl() {
    }

    @Override
    public void create(Route route) {
        try (Connection connection = ConnectToDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO route " +
                             "VALUES(?, ?, ?)")) {
            statement.setString(1, route.getId().toString());
            statement.setString(2, route.getStartPoint());
            statement.setString(3, route.getEndPoint());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Route getById(String id) {
        try (Connection connection = ConnectToDB.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM route WHERE id = ?");
            if (resultSet.first()) {
                return new Route(UUID.fromString(resultSet.getString("id")),
                        resultSet.getString("start_point"),
                        resultSet.getString("end_point"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Route route) {
        try (Connection connection = ConnectToDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE route SET start_point = ? , "
                             + "end_point = ? "
                             + "WHERE id = ?")) {
            statement.setString(1, route.getStartPoint());
            statement.setString(2, route.getEndPoint());
            statement.setString(3, route.getId().toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Route route) {
        try (Connection connection = ConnectToDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM route WHERE id = ?")) {
            statement.setString(1, route.getId().toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Route> getAll() {
        try (Connection connection = ConnectToDB.getConnection();
             Statement statement = connection.createStatement()) {
            List<Route> routes = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM route");
            while (resultSet.next()) {
                routes.add(new Route(UUID.fromString(resultSet.getString("id")),
                        resultSet.getString("start_point"),
                        resultSet.getString("end_point")));
            }
            return routes;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
