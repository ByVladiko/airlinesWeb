package dao.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {

    void create(final Connection connection, T obj) throws SQLException;

    T getById(final Connection connection, String id) throws SQLException;

    void update(final Connection connection, T obj) throws SQLException;

    void delete(final Connection connection, T obj) throws SQLException;

    List<T> getAll(final Connection connection) throws SQLException;
}
