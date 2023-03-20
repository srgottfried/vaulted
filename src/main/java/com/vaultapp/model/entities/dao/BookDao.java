package com.vaultapp.model.entities.dao;

import com.vaultapp.model.entities.Book;
import com.vaultapp.model.entities.User;
import com.vaultapp.utilities.JpaUtil;
import com.vaultapp.utilities.filters.BookFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

/**
 * Data Access Object for books.
 * Provides CRUD operations for {@link Book} entities.
 */
public class BookDao implements Dao<Book> {
    private EntityManager em;

    /**
     * Singleton instance of the DAO.
     */
    private static BookDao instance;

    static {
        instance = new BookDao(JpaUtil.getEntityManager());
    }

    /**
     * Creates a new instance of the DAO.
     *
     * @param entityManager the {@link EntityManager} to use for persistence operations
     */
    private BookDao(EntityManager entityManager) {
        this.em = entityManager;
    }

    /**
     * Returns the singleton instance of the DAO.
     *
     * @return the instance
     */
    public static BookDao getInstance() {
        return instance;
    }


    /**
     * Persists or updates the given book in the database.
     *
     * @param book the book to create or update
     */
    @Override
    public void create(Book book) {
        if (book.getId() != null && book.getId() > 0) {
            em.merge(book);
        } else {
            em.persist(book);
        }
    }


    /**
     * Returns a list of all books in the database.
     *
     * @return the list of books
     */
    @Override
    public List<Book> read() {
        return em.createQuery("select b from Book b", Book.class).getResultList();
    }


    /**
     * Deletes the book with the given ID from the database.
     *
     * @param id the ID of the book to delete
     */
    @Override
    public void delete(Long id) {
        if (em.find(Book.class, id) != null) {
            em.remove(em.find(Book.class, id));
        }
    }

    /**
     * Returns a list of books that match the given filter and search argument.
     * Only books belonging to the given user will be considered.
     *
     * @param user the user whose books should be considered
     * @param filter the filter to apply
     * @param arg the search argument
     * @return the list of matching books
     */
    public List<Book> findBy(User user, BookFilter filter, String arg) {
        Query q;

        if (filter == BookFilter.TITLE) {
            q = em.createQuery("select b from Book b join User u where u = :user and b.title = :title");
            q.setParameter("title", arg);
            q.setParameter("user", user);
        } else if (filter == BookFilter.AUTHOR) {
            q = em.createQuery("select b from Book b join User u where u = :user and  b.author = :author");
            q.setParameter("author", arg);
            q.setParameter("user", user);
        } else if (filter == BookFilter.ISBN) {
            q = em.createQuery("select b from Book b join User u where u = :user and  b.isbn = :isbn");
            q.setParameter("isbn", arg);
            q.setParameter("user", user);
        } else {
            return null;
        }

        try {
            return (List<Book>) q.getResultList();

        } catch (Exception e) {
            return null;
        }
    }
}
