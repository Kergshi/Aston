package org.example.repository;

import java.util.List;

public interface Repository<T, K> {
    T save(T t);

    T findById(K id);

    boolean deleteById(K id);

    List<T> findAll();

    boolean existsById(K id);

    void update(T t);
}
