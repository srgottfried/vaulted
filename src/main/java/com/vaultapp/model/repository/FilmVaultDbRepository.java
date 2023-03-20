package com.vaultapp.model.repository;

import com.vaultapp.model.entities.FilmVault;
import com.vaultapp.model.entities.dao.Dao;
import com.vaultapp.model.entities.dao.FilmVaultDao;
import com.vaultapp.utilities.JpaUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

/**
 * Repository class for accessing film vaults stored in a database through the FilmVaultDao.
 */
public class FilmVaultDbRepository implements Repository<FilmVault> {
    private EntityManager em;
    private Dao<FilmVault> filmVaultDao;
    private static FilmVaultDbRepository instance;

    static { instance = new FilmVaultDbRepository(JpaUtil.getEntityManager()); }

    /**
     * Private constructor to create a new instance of the FilmVaultDbRepository with a specified EntityManager.
     * @param entityManager the EntityManager to use for database operations
     */
    private FilmVaultDbRepository(EntityManager entityManager) {
        em = entityManager;
        filmVaultDao = FilmVaultDao.getInstance();
    }

    /**
     * Gets the singleton instance of the FilmVaultDbRepository.
     * @return the singleton instance of the FilmVaultDbRepository
     */
    public static FilmVaultDbRepository getInstance() { return instance; }

    /**
     * Adds a film vault to the database.
     * @param filmVault the film vault to add
     */
    @Override
    public void add(FilmVault filmVault) {
        try {
            em.getTransaction().begin();
            filmVaultDao.create(filmVault);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    /**
     * Gets a list of all film vaults in the database.
     * @return a list of all film vaults in the database
     */
    @Override
    public List<FilmVault> getAsList() {
        return filmVaultDao.read();
    }

    /**
     * Removes a film vault from the database.
     * @param id the id of the film vault to remove
     */
    @Override
    public void remove(Long id) {
        try {
            em.getTransaction().begin();
            filmVaultDao.delete(id);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
