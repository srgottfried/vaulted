package com.vaultapp.model.repository;

import com.vaultapp.model.entities.User;
import com.vaultapp.model.entities.dao.Dao;
import com.vaultapp.model.entities.dao.FilmDao;
import com.vaultapp.model.entities.Film;
import com.vaultapp.utilities.JpaUtil;
import com.vaultapp.utilities.filters.FilmFilter;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository class for accessing films stored in a database through the FilmDao.
 */
public class FilmDbRepository implements Repository<Film> {
    private EntityManager em;
    private Dao<Film> filmDao;
    private static FilmDbRepository instance;

    static {
        instance = new FilmDbRepository(JpaUtil.getEntityManager());
    }

    /**
     * Private constructor to create a new instance of the FilmDbRepository with a specified EntityManager.
     *
     * @param entityManager the EntityManager to use for database operations
     */
    private FilmDbRepository(EntityManager entityManager) {
        em = entityManager;
        filmDao = FilmDao.getInstance();
    }

    /**
     * Gets the singleton instance of the FilmDbRepository.
     *
     * @return the singleton instance of the FilmDbRepository
     */
    public static FilmDbRepository getInstance() {
        return instance;
    }

    /**
     * Adds a film to the database.
     *
     * @param film the film to add
     */
    @Override
    public void add(Film film) {
        try {
            em.getTransaction().begin();
            filmDao.create(film);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    /**
     * Finds a film with a specific TMDB id.
     *
     * @param tmid the TMDB id of the film to find
     * @return the film with the specified TMDB id, or null if no such film exists
     */
    public Film findByTmid(String tmid) {
        if (getAsList() == null || getAsList().isEmpty()) {
            return null;
        }
        List<Film> l = getAsList().stream().filter(s -> String.valueOf(s.getTmdbId()).equals(tmid.trim())).collect(Collectors.toList());
        if (l.isEmpty()) {
            return null;
        }
        return l.get(0);
    }

    /**
     * Gets a list of all films in the database.
     *
     * @return a list of all films in the database
     */
    @Override
    public List<Film> getAsList() {
        return filmDao.read();
    }

    /**
     * Removes a film from the database.
     *
     * @param id the id of the film to remove
     */
    @Override
    public void remove(Long id) {
        try {
            em.getTransaction().begin();
            filmDao.delete(id);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    /**
     * Finds a list of films that match a specified filter and argument.
     *
     * @param user   the user making the request
     * @param filter the filter to apply
     * @param arg    the argument to use for the filter
     * @return a list of films that match the specified filter and argument
     */
    public List<Film> findBy(User user, FilmFilter filter, String arg) {
        return ((FilmDao) filmDao).findBy(user, filter, arg);
    }
}
