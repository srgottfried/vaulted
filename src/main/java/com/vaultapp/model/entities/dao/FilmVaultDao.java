package com.vaultapp.model.entities.dao;

import com.vaultapp.model.entities.FilmVault;
import com.vaultapp.utilities.JpaUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

/**
 * FilmVaultDao is a class that implements the Dao interface for the FilmVault entity. It has methods for creating, reading, updating, and deleting FilmVault objects from a database.
 */
public class FilmVaultDao implements Dao<FilmVault> {
    private EntityManager em;

    private static FilmVaultDao instance;

    static {
        instance = new FilmVaultDao(JpaUtil.getEntityManager());
    }

    /**
     * Returns the singleton instance of FilmVaultDao.
     *
     * @return the singleton instance of FilmVaultDao
     */
    public static FilmVaultDao getInstance() {
        return instance;
    }

    /**
     * Returns the singleton instance of FilmVaultDao.
     *
     * @return the singleton instance of FilmVaultDao
     */
    public FilmVaultDao(EntityManager em) {
        this.em = em;
    }


    /**
     * Creates or updates a FilmVault object in the database.
     * If the FilmVault object has an id that is not null and greater than 0, it will be updated in the database.
     * Otherwise, it will be inserted into the database as a new FilmVault.
     *
     * @param filmVault the FilmVault object to be created or updated in the database
     */
    @Override
    public void create(FilmVault filmVault) {
        if (filmVault.getId() != null && filmVault.getId() > 0) {
            em.merge(filmVault);
        } else {
            em.persist(filmVault);
        }
    }

    /**
     * Reads all FilmVault objects from the database.
     *
     * @return a List of FilmVault objects representing all FilmVaults in the database
     */
    @Override
    public List<FilmVault> read() {
        return em.createQuery("select f from FilmVault f", FilmVault.class).getResultList();
    }

    /**
     * Deletes a FilmVault object from the database.
     *
     * @param id the id of the FilmVault object to be deleted
     */
    @Override
    public void delete(Long id) {
        if (em.find(FilmVault.class, id) != null) {
            em.remove(em.find(FilmVault.class, id));
        }
    }
}
