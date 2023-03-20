package com.vaultapp.model.entities.dao;

import com.vaultapp.model.entities.Film;
import com.vaultapp.model.entities.User;
import com.vaultapp.utilities.JpaUtil;
import com.vaultapp.utilities.filters.FilmFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

/**
 * FilmDao is a class that implements the Dao interface for the Film entity.
 * It has methods for creating, reading, updating, and deleting Film objects from a database.
 */
public class FilmDao implements Dao<Film> {
    private EntityManager em;

    /**
     * Constructs a new FilmDao object with the given EntityManager.
     *
     * @param entityManager the EntityManager to be used by this FilmDao
     */
    private static FilmDao instance;

    static {
        instance = new FilmDao(JpaUtil.getEntityManager());
    }

    private FilmDao(EntityManager entityManager) {
        em = entityManager;
    }

    /**
     * Returns the singleton instance of FilmDao.
     *
     * @return the singleton instance of FilmDao
     */
    public static FilmDao getInstance() {
        return instance;
    }

    /**
     * Creates or updates a Film object in the database.
     * If the Film object has an id that is not null and greater than 0, it will be updated in the database.
     * Otherwise, it will be inserted into the database as a new Film.
     *
     * @param film the Film object to be created or updated in the database
     */
    @Override
    public void create(Film film) {
        if (film.getId() != null && film.getId() > 0) {
            em.merge(film);
        } else {
            em.persist(film);
        }
    }

    /**
     * Reads all Film objects from the database.
     *
     * @return a List of Film objects representing all Films in the database
     */
    @Override
    public List<Film> read() {
        return em.createQuery("select f from Film f", Film.class).getResultList();
    }

    /**
     * Deletes a Film object from the database.
     *
     * @param id the id of the Film object to be deleted
     */
    @Override
    public void delete(Long id) {
        if (em.find(Film.class, id) != null) {
            em.remove(em.find(Film.class, id));
        }
    }

    /**
     * Finds Film objects in the database that match the given search criteria.
     *
     * @param user the User object to search for Films belonging to
     * @param filter the FilmFilter to use for searching
     * @param arg the search argument to use
     * @return a List of Film objects matching the search criteria, or null if no Films were found
     */
    public List<Film> findBy(User user, FilmFilter filter, String arg) {
        Query q;

        if (filter == FilmFilter.TITLE) {
            q = em.createQuery("select f from Film f join User u where u = :user and f.title = :title");
            q.setParameter("title", arg);
            q.setParameter("user", user);
        } else if (filter == FilmFilter.TMID) {
            q = em.createQuery("select f from Film f join User u where u = :user and f.tmdbId = :tmid");
            q.setParameter("tmid", arg);
            q.setParameter("user", user);
        } else {
            return null;
        }

        try {
            return (List<Film>) q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
