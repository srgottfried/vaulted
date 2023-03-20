package com.vaultapp.controller;

import com.vaultapp.login.UserSession;
import com.vaultapp.model.entities.BookVault;
import com.vaultapp.model.entities.FilmVault;
import com.vaultapp.model.entities.User;
import com.vaultapp.model.entities.Vault;
import com.vaultapp.model.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * The `NameInputController` class is a controller class for the name input view. It handles user input when
 * creating a new film or book vault.
 */
public class NameInputController {


    @FXML
    public TextField txfVaultName;
    @FXML
    public Button btnConfirmName;
    private UserSession session = UserSession.getInstance();

    /**
     * Handles the user's input when creating a new film or book vault. If the user's input is not empty and the
     * entered name does not already exist in the list of vaults, the method creates a new film or book vault and
     * adds it to the active user's list of vaults.
     * @param actionEvent the ActionEvent object that triggered the method
     */
    public void onNameConfirmClick(ActionEvent actionEvent) {

        User activeUser = session.getLoggedUser();
        String name = txfVaultName.getText();

        if (!name.isEmpty() && ChooseVaultDialogController.getVaultList().stream().noneMatch(vault -> vault.getName().equals(name))) {

            if (ChooseVaultDialogController.isChoosingFilms()) {

                FilmVault vault = new FilmVault(name);
                vault.setOwner(activeUser);
                activeUser.addVault(vault);
                MainGUIController.setSelectedVault(vault);
                UserRepository.getInstance().add(activeUser);

            } else {

                BookVault vault = new BookVault(name);
                vault.setOwner(activeUser);
                activeUser.addVault(vault);
                UserRepository.getInstance().add(activeUser);
                // TODO TESTING
                MainGUIController.setSelectedVault(activeUser.findVaultByName(vault.getName()).get(0));

            }

            Stage stage = (Stage) btnConfirmName.getScene().getWindow();
            stage.close();

        }

    }
}
