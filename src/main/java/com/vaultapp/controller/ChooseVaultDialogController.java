/**
 * The ChooseVaultDialogController is a controller class for the Choose Vault Dialog view. It allows users
 * to select an existing vault or add a new vault.
 *
 * @author Vault App
 */
package com.vaultapp.controller;

import com.vaultapp.MainGUI;
import com.vaultapp.login.UserSession;
import com.vaultapp.model.entities.Vault;
import com.vaultapp.model.repository.UserRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static atlantafx.base.theme.Styles.*;

public class ChooseVaultDialogController {

    /**
     * ObservableList that stores the list of vaults.
     */
    private static ObservableList<Vault> vaultList;

    /**
     * TableView that displays the list of vaults.
     */
    @FXML
    public TableView<Vault> vaultsTable;

    /**
     * Button that allows the user to select a vault.
     */
    @FXML
    public Button btnSelect;

    /**
     * Button that cancels the Choose Vault Dialog.
     */
    @FXML
    public Button btnCancel;

    /**
     * Label that displays a prompt to the user.
     */
    @FXML
    public Label prompt;

    /**
     * Button that allows the user to add a new vault.
     */
    @FXML
    public Button btnAdd;

    /**
     * Boolean value indicating whether the user is choosing films or not.
     */
    private static boolean choosingFilms;

    /**
     * Button that allows the user to delete a vault
     */
    @FXML
    public Button btnDelete;

    /**
     * Returns the ObservableList of vaults.
     *
     * @return the ObservableList of vaults
     */
    public static ObservableList<Vault> getVaultList() {
        return vaultList;
    }

    /**
     * Returns a boolean value indicating whether the user is choosing films or not.
     *
     * @return a boolean value indicating whether the user is choosing films or not
     */
    public static boolean isChoosingFilms() {
        return choosingFilms;
    }

    /**
     * Sets the value of the choosingFilms field.
     *
     * @param choosingFilms a boolean value indicating whether the user is choosing films or not
     */
    public static void setChoosingFilms(boolean choosingFilms) {
        ChooseVaultDialogController.choosingFilms = choosingFilms;
    }

    /**
     * Sets the value of the vaultList field.
     *
     * @param vaultList the list of vaults to set
     */
    public static void setVaultList(List<Vault> vaultList) {


        ChooseVaultDialogController.vaultList = FXCollections.observableList(vaultList);

    }

    /**
     * Initializes the Choose Vault Dialog view.
     */
    public void initialize() {

        btnDelete.setGraphic(new ImageView(new Image(Objects.requireNonNull(MainGUI.class.getResource("trash.png")).toString())));
        prompt.getStyleClass().add(TITLE_2);
        vaultsTable.getStyleClass().add(STRIPED);

        vaultsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        vaultsTable.setItems(vaultList);

        TableColumn<Vault, String> nameCol = new TableColumn<>("Vault name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        vaultsTable.getColumns().add(nameCol);
    }

    /**
     * Handles button clicks for the Choose Vault Dialog view.
     *
     * @param actionEvent the ActionEvent that triggered the button click
     * @throws IOException if an I/O error occurs when loading the view
     */
    public void onBtnClick(ActionEvent actionEvent) throws IOException {

        if (actionEvent.getSource().equals(btnSelect)) {

            MainGUIController.setSelectedVault(vaultsTable.getSelectionModel().getSelectedItem());

        } else if (actionEvent.getSource().equals(btnAdd)) {

            FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("vaultNameInput-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Name your vault");
            stage.initOwner(MainGUI.getMainStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.showAndWait();
        }

        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    /**
     * It deletes the selected vault from the user's vault list and refreshes the table
     *
     * @param actionEvent The event that triggered the action.
     */
    public void onDeleteClick(ActionEvent actionEvent) {

        Vault vault = vaultsTable.getSelectionModel().getSelectedItem();
        UserSession.getInstance().getLoggedUser().removeVault(vault);

        if (vault.equals(MainGUIController.getSelectedVault())) {

            MainGUIController.setSelectedVault(null);

        }

        vaultList = choosingFilms ?
                FXCollections.observableArrayList(UserSession.getInstance().getLoggedUser().getFilmVaults()) :
                FXCollections.observableArrayList(UserSession.getInstance().getLoggedUser().getBookVaults());

        vaultsTable.setItems(vaultList);
        vaultsTable.refresh();
        UserRepository.getInstance().add(UserSession.getInstance().getLoggedUser());

    }
}
