package com.vaultapp.model.entities.dao;

import java.util.List;

/**
 * Data Access Object interface.
 * Provides basic CRUD operations for a given entity type.
 *
 * @param <T> the type of the entity
 */
public interface Dao<T> {
    /**
     * Persists or updates the given entity in the database.
     *
     * @param t the entity to create or update
     */
    void create(T t);

    /**
     * Returns a list of all entities of type T in the database.
     *
     * @return the list of entities
     */
    List<T> read();

    /**
     * Deletes the entity with the given ID from the database.
     *
     * @param id the ID of the entity to delete
     */
    void delete(Long id);
}
