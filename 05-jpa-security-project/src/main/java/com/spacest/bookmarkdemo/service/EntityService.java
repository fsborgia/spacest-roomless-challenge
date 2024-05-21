package com.spacest.bookmarkdemo.service;

import java.util.List;

public interface EntityService<E> {
    List<E> findAll();

    E findById(int id);

    E save(E element);

    void deleteById(int id);
}
