package dao;

import java.util.List;

public interface DAO<T> {
    public abstract void create(T adr);
    public abstract T getById(int id);
    public abstract void update(T adr);
    public abstract void delete(T adr);
    public abstract List<T> getAll();
}
