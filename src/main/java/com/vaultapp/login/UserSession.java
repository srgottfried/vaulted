package com.vaultapp.login;

import com.vaultapp.model.entities.User;
import com.vaultapp.model.repository.UserRepository;
import java.time.LocalDateTime;

public class UserSession {
    private static UserSession instance;
    private User loginUser;

    /**
     * Initializes the `instance` field with a new instance of the `UserSession` class.
     */
    static {
        instance = new UserSession();
    }

    /**
     * Returns the current instance of the `UserSession` class.
     *
     * @return the current `UserSession` instance
     */
    public static UserSession getInstance() {
        return instance;
    }

    /**
     * Attempts to log in the specified user by checking their credentials against
     * the user repository. If the login is successful, sets the `loginUser` field to
     * the specified user and returns `true`, otherwise returns `false`.
     *
     * @param user the user to log in
     * @return `true` if the login was successful, `false` otherwise
     */
    public boolean login(User user) {

        User u = UserRepository.getInstance().find(user.getName());
        logout();
        if (user.equals(u)) {
            loginUser = u;
            return true;
        }
        return false;
    }

    /**
     * Attempts to log out the currently logged in user by setting the `loginUser`
     * field to `null`. If the `loginUser` field was already `null`, returns `false`,
     * otherwise returns `true`.
     *
     * @return `true` if the logout was successful, `false` otherwise
     */
    public boolean logout() {
        if (loginUser != null) {
            loginUser.setLastConnection(LocalDateTime.now());
            UserRepository.getInstance().add(loginUser);
            loginUser = null;
            return true;
        }
        return false;
    }

    /**
     * Returns the currently logged in user, or `null` if no user is logged in.
     *
     * @return the currently logged in user, or `null` if no user is logged in
     */
    public User getLoggedUser(){
        return loginUser;
    }

    /**
     * Returns `true` if a user is currently logged in, `false` otherwise.
     *
     * @return `true` if a user is currently logged in, `false` otherwise
     */
    public boolean inSession() {
        return loginUser != null;
    }
}

