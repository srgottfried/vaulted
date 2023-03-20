package com.vaultapp.model.entities.dao;

import com.vaultapp.model.entities.User;
import com.vaultapp.utilities.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

/**
 * Data Access Object for the {@link User} entity.
 */
public class UserDao implements Dao<User> {
    private EntityManager em;

    private static UserDao instance;

    static { instance = new UserDao(JpaUtil.getEntityManager()); }

    /**
     * Private constructor to create a new instance of the UserDao with a specified EntityManager.
     * @param entityManager the EntityManager to use for database operations
     */
    private UserDao(EntityManager entityManager) {
        em = entityManager;
    }

    /**
     * Gets the singleton instance of the UserDao.
     * @return the singleton instance of the UserDao
     */
    public static UserDao getInstance() { return instance; }

    /**
     * Creates a new user in the database, or updates an existing one.
     * @param user the user to create or update
     */
    @Override
    public void create(User user) {
        if (user.getId() != null && user.getId() > 0) {
            em.merge(user);
        } else {
            em.persist(user);
        }
    }

    /**
     * Gets a list of all users in the database.
     * @return a list of all users in the database
     */
    @Override
    public List<User> read() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    /**
     * Deletes a user from the database.
     * @param id the id of the user to delete
     */
    @Override
    public void delete(Long id) {
        if (em.find(User.class, id) != null) {
            em.remove(em.find(User.class, id));
        }
    }

    /**
     * Finds a user with a specific name.
     * @param name the name of the user to find
     * @return the user with the specified name, or null if no such user exists
     */
    public User find(String name) {
        Query q = em.createQuery("select u from User u where name = :name");
        q.setParameter("name", name);
        try {
            return (User) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}

