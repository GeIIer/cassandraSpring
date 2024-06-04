package com.example.cassandraspring.services;

import java.util.List;
import java.util.UUID;

public interface BaseService<T> {
    List<T> getAll();
    T getById(UUID id);
    T create(T table);
    T update(T table);
    void deleteById(UUID id);

}
