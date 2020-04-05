package dao.api;

import java.util.List;

public interface DAO<T> {
    void create(T obj);

    T getById(String id);

    void update(T obj);

    void delete(T obj);

    List<T> getAll();
}
