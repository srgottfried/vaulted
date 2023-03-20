package com.vaultapp.tests.login;

import com.vaultapp.login.UserSession;
import com.vaultapp.model.entities.User;
import com.vaultapp.model.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserSessionTest {
    private UserSession userSession;
    private User user;

    @Before
    public void setup() {
        userSession = UserSession.getInstance();
        user = new User("root", "root");
        UserRepository.getInstance().add(user);
    }

    @Test
    public void testLogin() {
        assertTrue(userSession.login(user));
        assertTrue(userSession.inSession());
        assertTrue(userSession.login(user));
    }

    @Test
    public void testLogout() {
        userSession.logout();
        assertFalse(userSession.logout());
        assertTrue(userSession.login(user));
        assertTrue(userSession.inSession());
        assertTrue(userSession.logout());
        assertFalse(userSession.inSession());
        assertFalse(userSession.logout());
    }

    @Test
    public void testGetLoggedUser() {
        assertTrue(userSession.login(user));
        assertEquals(userSession.getLoggedUser(), user);
        assertTrue(userSession.logout());
        assertNull(userSession.getLoggedUser());
    }
}

