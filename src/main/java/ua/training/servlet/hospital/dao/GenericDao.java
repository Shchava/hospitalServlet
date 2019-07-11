package ua.training.servlet.hospital.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> extends AutoCloseable{
    boolean create(T entity);
    List<T> findAll();
    List<T> find(int start, int count);
    Optional<T> findById(long id);
    int getNumberOfRows();
    boolean update(T entity);
    boolean delete(long id);
    void close();
}
