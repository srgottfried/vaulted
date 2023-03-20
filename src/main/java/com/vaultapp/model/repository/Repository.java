package com.vaultapp.model.repository;

import java.util.List;

/**
 * Interface for a repository class. A repository is a class that manages access to a data source, such as a database.
 *
 * @param <T> the type of the objects managed by the repository
 */
public interface Repository<T> {

    /**
     * Adds an object to the data source.
     * @param t the object to add
     */
    void add(T t);

    /**
     * Gets a list of all objects in the data source.
     * @return a list of all objects in the data source
     */
    List<T> getAsList();

    /**
     * Removes an object from the data source.
     * @param id the id of the object to remove
     */
    void remove(Long id);

}

