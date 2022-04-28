package pl.first.firstjava;

import pl.first.firstjava.exception.DaoException;

public interface Dao<T> extends AutoCloseable {
    T read() throws DaoException;

    void write(T obj) throws DaoException;
}
