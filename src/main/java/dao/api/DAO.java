package dao.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
    void create(T obj) throws SQLException;

    T getById(String id) throws SQLException;

    void update(final Connection connection, T obj) throws SQLException;

    void delete(T obj) throws SQLException;

    List<T> getAll() throws SQLException;
}
