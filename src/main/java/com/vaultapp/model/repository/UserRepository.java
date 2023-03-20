package com.vaultapp.model.repository;


import com.vaultapp.model.entities.dao.UserDao;
import com.vaultapp.model.entities.User;
import com.vaultapp.utilities.JpaUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

/**
 * Repository class for accessing users stored in a database through the UserDao.
 */
public class UserRepository implements Repository<User> {
    private EntityManager em;
    private UserDao userDao;
    private static UserRepository instance;

    static {
        instance = new UserRepository(JpaUtil.getEntityManager());
    }

    /**
     * Private constructor to create a new instance of the UserRepository with a specified EntityManager.
     *
     * @param entityManager the EntityManager to use for database operations
     */
    private UserRepository(EntityManager entityManager) {
        em = entityManager;
        userDao = UserDao.getInstance();
    }

    /**
     * Gets the singleton instance of the UserRepository.
     *
     * @return the singleton instance of the UserRepository
     */
    public static UserRepository getInstance() {
        return instance;
    }

    /**
     * Adds a user to the database.
     *
     * @param user the user to add
     */
    @Override
    public void add(User user) {
        try {
            em.getTransaction().begin();
            userDao.create(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    /**
     * Gets a list of all users in the database.
     *
     * @return a list of all users in the database
     */
    @Override
    public List<User> getAsList() {
        return userDao.read();
    }

    /**
     * Removes a user from the database.
     *
     * @param id the id of the user to remove
     */
    @Override
    public void remove(Long id) {
        try {
            em.getTransaction().begin();
            userDao.delete(id);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    /**
     * Finds a user in the database by their name.
     *
     * @param name the name of the user to find
     * @return the found user, or null if no user with the specified name was found
     */
    public User find(String name) {
        return userDao.find(name);
    }
}
