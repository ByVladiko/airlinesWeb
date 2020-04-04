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

    private static String createRoute = "INSERT INTO route VALUES(?, ?, ?)";
    private static String getByIdRoute = "SELECT * FROM route WHERE id = ?";
    private static String updateRoute = "UPDATE route SET start_point = ?, end_point = ? WHERE id = ?";
    private static String deleteRoute = "DELETE FROM route WHERE id = ?";
    private static String getAllRoute = "SELECT * FROM route";

    @Override
    public void create(Route route) {
        try (Connection connection = ConnectToDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(createRoute)) {
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
             PreparedStatement statement = connection.prepareStatement(getByIdRoute)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
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
             PreparedStatement statement = connection.prepareStatement(updateRoute)) {
            statement.setString(1, route.getStartPoint());
            statement.setString(2, route.getEndPoint());
            statement.setString(3, route.getId().toString());
            if (statement.executeUpdate() == 0) {
                create(route);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Route route) {
        try (Connection connection = ConnectToDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteRoute)) {
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
            ResultSet resultSet = statement.executeQuery(getAllRoute);
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
