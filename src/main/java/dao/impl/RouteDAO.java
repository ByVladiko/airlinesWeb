package dao.impl;

import dao.api.DAO;
import model.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static util.EntityFactoryDAO.createRoute;

public class RouteDAO implements DAO<Route> {

    public RouteDAO() {
    }

    private static final Logger logger = LoggerFactory.getLogger(RouteDAO.class);

    private static final String CREATE_ROUTE = "INSERT INTO route VALUES(?, ?, ?)";
    private static final String GET_ROUTE_BY_ID = "SELECT * FROM route WHERE id = ?";
    private static final String UPDATE_ROUTE = "UPDATE route SET start_point = ?, end_point = ? WHERE id = ?";
    private static final String DELETE_ROUTE = "DELETE FROM route WHERE id = ?";
    private static final String SELECT_ALL_ROUTES = "SELECT * FROM route";

    @Override
    public void create(final Connection connection, Route route) {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_ROUTE)) {
            statement.setString(1, route.getId().toString());
            statement.setString(2, route.getStartPoint());
            statement.setString(3, route.getEndPoint());
            int result = statement.executeUpdate();
            if (result == 0) {
                throw new SQLException("Record has not been inserted");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    public Route getById(final Connection connection, String id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(GET_ROUTE_BY_ID)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createRoute(resultSet);
            } else {
                throw new SQLException("Can't get record by this id");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        throw new SQLException("Unsuccessful operation");
    }

    @Override
    public void update(final Connection connection, Route route) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ROUTE)) {
            statement.setString(1, route.getStartPoint());
            statement.setString(2, route.getEndPoint());
            statement.setString(3, route.getId().toString());
            int result = statement.executeUpdate();
            if (result == 0) {
                throw new SQLException("No one record have been updated");
            } else if (result > 1) {
                throw new SQLException("More than one record has been updated");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    public void delete(final Connection connection, Route route) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ROUTE)) {
            statement.setString(1, route.getId().toString());
            int result = statement.executeUpdate();
            if (result == 0) {
                throw new SQLException("No one record has been deleted");
            } else if (result > 1) {
                throw new SQLException("More than one record has been deleted");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    public List<Route> getAll(final Connection connection) {
        try (Statement statement = connection.createStatement()) {
            List<Route> routes = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_ROUTES);
            while (resultSet.next()) {
                routes.add(createRoute(resultSet));
            }
            return routes;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
