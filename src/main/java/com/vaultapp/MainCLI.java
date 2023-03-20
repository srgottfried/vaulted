package com.vaultapp;

import com.vaultapp.controller.CommandController;
import com.vaultapp.model.entities.User;
import com.vaultapp.model.repository.UserRepository;
import com.vaultapp.utilities.JpaUtil;


/**
 * Defines the logic of the program.
 *
 * @author Manuel Landín Gómez
 * @author Sergio Alonso Pazo
 */
public class MainCLI {

    public static void startApp() {
        // PERSISTENT TEST DATA
        User u = new User("root", "root");
        UserRepository.getInstance().add(u);

        // START APP
        new CommandController();

        JpaUtil.close();
    }

}
