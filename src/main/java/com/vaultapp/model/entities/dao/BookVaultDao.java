package com.vaultapp.model.entities.dao;

import com.vaultapp.model.entities.BookVault;
import com.vaultapp.utilities.JpaUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

/**
 * Data Access Object for book vaults.
 * Provides CRUD operations for {@link BookVault} entities.
 */
public class BookVaultDao implements Dao<BookVault> {
    private EntityManager em;

    /**
     * Singleton instance of the DAO.
     */
    private static BookVaultDao instance;

    static {
        instance = new BookVaultDao(JpaUtil.getEntityManager());
    }

    /**
     * Returns the singleton instance of the DAO.
     *
     * @return the instance
     */
    public static BookVaultDao getInstance() {
        return instance;
    }

    /**
     * Creates a new instance of the DAO.
     *
     * @param em the {@link EntityManager} to use for persistence operations
     */
    public BookVaultDao(EntityManager em) {
        this.em = em;
    }

    /**
     * Persists or updates the given book vault in the database.
     *
     * @param bookVault the book vault to create or update
     */
    @Override
    public void create(BookVault bookVault) {
        if (bookVault.getId() != null && bookVault.getId() > 0) {
            em.merge(bookVault);
        } else {
            em.persist(bookVault);
        }
    }

    /**
     * Returns a list of all book vaults in the database.
     *
     * @return the list of book vaults
     */
    @Override
    public List<BookVault> read() {
        return em.createQuery("select b from BookVault b", BookVault.class).getResultList();
    }

    /**
     * Returns a list of all book vaults in the database.
     *
     * @return the list of book vaults
     */
    @Override
    public void delete(Long id) {
        if (em.find(BookVault.class, id) != null) {
            em.remove(em.find(BookVault.class, id));
        }
    }

}
