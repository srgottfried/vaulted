package com.vaultapp.utilities;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Singleton for EntityManagerFactory generation. Every instance of EntityManager
 * is provided for a single instance of EntityManagerFactory.
 *
 * @author Manuel Landín Gómez
 * @author Sergio Alonso Pazo
 */
public class JpaUtil {
    private static final EntityManagerFactory entityManagerrFactory = buildEntityManagerFactory();
    private static final EntityManager em;

    static { em = entityManagerrFactory.createEntityManager(); }

    private static EntityManagerFactory buildEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("vault_local");
    }

    /**
     * Generate an EntityManager connection instance.
     *
     * @return the EntityManager instance.
     */
    public static EntityManager getEntityManager() {
        return em;
    }

    /**
     * Close the EntityManager instance at the end of the program.
     * Method <code>close()</code> must be the last one.
     */
    public static void close() {
        try {
            em.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
