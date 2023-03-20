package com.vaultapp.model.repository;

import com.vaultapp.model.entities.User;
import com.vaultapp.model.entities.dao.BookDao;
import com.vaultapp.model.entities.dao.Dao;
import com.vaultapp.model.entities.Book;
import com.vaultapp.utilities.JpaUtil;
import com.vaultapp.utilities.filters.BookFilter;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository class for accessing books stored in a database through the BookDao.
 */
public class BookDbRepository implements Repository<Book> {
    private EntityManager em;
    private Dao<Book> bookDao;
    private static BookDbRepository instance;

    static {
        instance = new BookDbRepository(JpaUtil.getEntityManager());
    }

    /**
     * Private constructor to create a new instance of the BookDbRepository with a specified EntityManager.
     *
     * @param entityManager the EntityManager to use for database operations
     */
    private BookDbRepository(EntityManager entityManager) {
        em = entityManager;
        bookDao = BookDao.getInstance();
    }

    /**
     * Gets the singleton instance of the BookDbRepository.
     *
     * @return the singleton instance of the BookDbRepository
     */
    public static BookDbRepository getInstance() {
        return instance;
    }

    /**
     * Adds a book to the database.
     *
     * @param book the book to add
     */
    @Override
    public void add(Book book) {
        try {
            em.getTransaction().begin();
            bookDao.create(book);
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
    public List<Book> getAsList() {
        return bookDao.read();
    }

    /**
     * Finds a book with a specific ISBN.
     *
     * @param isbn the ISBN of the book to find
     * @return the book with the specified ISBN, or null if no such book exists
     */
    public Book findByIsbn(String isbn) {
        if (getAsList() == null || getAsList().isEmpty()) {
            return null;
        }
        List<Book> l = getAsList().stream().filter(s -> s.getIsbn().equals(isbn.trim())).collect(Collectors.toList());
        if (l.isEmpty()) {
            return null;
        }
        return l.get(0);
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
            bookDao.delete(id);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    /**
     * Finds a list of books that match a specified filter and argument.
     *
     * @param user   the user making the request
     * @param filter the filter to apply
     * @param arg    the argument to use for the filter
     * @return a list of books that match the specified filter and argument
     */
    public List<Book> findBy(User user, BookFilter filter, String arg) {
        return ((BookDao) bookDao).findBy(user, filter, arg);
    }
}
