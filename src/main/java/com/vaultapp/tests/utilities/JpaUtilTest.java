package com.vaultapp.tests.utilities;

import static org.junit.jupiter.api.Assertions.*;

import com.vaultapp.utilities.JpaUtil;
import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

public class JpaUtilTest {

    @Test
    void testGetEntityManager() {
        EntityManager em1 = JpaUtil.getEntityManager();
        EntityManager em2 = JpaUtil.getEntityManager();
        assertSame(em1, em2);
    }

    @AfterAll
    static void testClose() {
        JpaUtil.close();
    }
}
