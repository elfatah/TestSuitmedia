package com.example.masboyjahat.testsuitmedia.domain.repository;


import java.util.List;

/**
 * A sample repository with CRUD operations on a model.
 */
public interface Repository<T> {

    boolean insert(T model);

    boolean update(T model);

    T get(Object id);

    boolean delete(T model);
    List<T>getAll();

    interface guestList{
        void getAll();
    }

}
