package dao.impl;

import dao.api.DAO;
import model.Route;
import repository.DatabaseConnectivityProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class RouteDAO implements DAO<Route> {

    public RouteDAO() {
    }

    private static final String CREATE_ROUTE = "INSERT INTO route VALUES(?, ?, ?)";
    private static final String GET_ROUTE_BY_ID = "SELECT * FROM route WHERE id = ?";
    private static final String UPDATE_ROUTE = "UPDATE route SET start_point = ?, end_point = ? WHERE id = ?";
    private static final String DELETE_ROUTE = "DELETE FROM route WHERE id = ?";
    private static final String SELECT_ALL_ROUTES = "SELECT * FROM route";

    @Override
    public void create(Route route) {
        try (Connection connection = DatabaseConnectivityProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_ROUTE)) {
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
        try (Connection connection = DatabaseConnectivityProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ROUTE_BY_ID)) {
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
    public void update(final Connection connection, Route route) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ROUTE)) {
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
        try (Connection connection = DatabaseConnectivityProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ROUTE)) {
            statement.setString(1, route.getId().toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Route> getAll() {
        try (Connection connection = DatabaseConnectivityProvider.getConnection();
             Statement statement = connection.createStatement()) {
            List<Route> routes = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_ROUTES);
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
