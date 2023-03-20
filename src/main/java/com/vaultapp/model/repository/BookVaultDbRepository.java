package com.vaultapp.model.repository;

import com.vaultapp.model.entities.BookVault;
import com.vaultapp.model.entities.dao.BookVaultDao;
import com.vaultapp.model.entities.dao.Dao;
import com.vaultapp.utilities.JpaUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

/**
 * Repository class for accessing books stored in a database through the BookVaultDao.
 */
public class BookVaultDbRepository implements Repository<BookVault> {
    private EntityManager em;
    private Dao<BookVault> bookVaultDao;
    private static BookVaultDbRepository instance;

    static {
        instance = new BookVaultDbRepository(JpaUtil.getEntityManager());
    }

    /**
     * Private constructor to create a new instance of the BookVaultDbRepository with a specified EntityManager.
     *
     * @param entityManager the EntityManager to use for database operations
     */
    private BookVaultDbRepository(EntityManager entityManager) {
        em = entityManager;
        bookVaultDao = BookVaultDao.getInstance();
    }

    /**
     * Gets the singleton instance of the BookVaultDbRepository.
     *
     * @return the singleton instance of the BookVaultDbRepository
     */
    public static BookVaultDbRepository getInstance() {
        return instance;
    }

    /**
     * Adds a book to the database.
     *
     * @param bookVault the book to add
     */
    @Override
    public void add(BookVault bookVault) {
        try {
            em.getTransaction().begin();
            bookVaultDao.create(bookVault);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    /**
     * Gets a list of all books in the database.
     *
     * @return a list of all books in the database
     */
    @Override
    public List<BookVault> getAsList() {
        return bookVaultDao.read();
    }

    /**
     * Removes a book from the database.
     *
     * @param id the id of the book to remove
     */
    @Override
    public void remove(Long id) {
        try {
            em.getTransaction().begin();
            bookVaultDao.delete(id);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
