package com.vaultapp.controller;

import com.vaultapp.MainGUI;
import com.vaultapp.login.UserSession;
import com.vaultapp.model.entities.User;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

import static atlantafx.base.theme.Styles.*;

/**
 * This class handles the UI and user input for logging in to the Vault application.
 */
public class LoginGUIController {

    @FXML
    public Label lblErrMsg;
    @FXML
    public TextField txfUsername;
    @FXML
    public PasswordField pwfPassword;
    @FXML
    private Label lblAppName;
    @FXML
    private Button btnLogin;

    /**
     * Initializes the UI elements and sets the appropriate style classes.
     */
    public void initialize() {

        lblAppName.getStyleClass().add(TITLE_1);
        lblAppName.setStyle("-fx-font-size: 60");
        btnLogin.getStyleClass().add(LARGE);
        lblErrMsg.getStyleClass().add(DANGER);
        btnLogin.setGraphic(new ImageView(new Image(Objects.requireNonNull(MainGUI.class.getResource("box-arrow-in-down.png")).toString())));
        lblAppName.setGraphic(new ImageView(new Image(Objects.requireNonNull(MainGUI.class.getResource("box.png")).toString())));

    }

    /**
     * Handles the user clicking the login button. Validates the entered username and password and logs the user in if
     * they are valid.
     * @param actionEvent the ActionEvent object generated by the login button being clicked
     */
    public void onLoginClick(ActionEvent actionEvent) {
        String username = txfUsername.getText();
        String password = pwfPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            incorrectAnimation();
            return;
        }

        User user = new User(username, password);

        if (!UserSession.getInstance().login(user)) {
            incorrectAnimation();
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("main-view.fxml"));
        try {
            Stage current = (Stage) btnLogin.getScene().getWindow();
            current.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays a warning text when the user inputs incorrect or no credentials.
     */
    private void incorrectAnimation() {

        Timeline anim = new Timeline();

        KeyValue errMsgOpacity = new KeyValue(lblErrMsg.opacityProperty(), 1.0);
        KeyFrame keyFrame1 = new KeyFrame(Duration.millis(800), errMsgOpacity);
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(1500));

        anim.getKeyFrames().addAll(keyFrame1, keyFrame2);
        anim.setCycleCount(2);
        anim.setAutoReverse(true);
        anim.play();

    }
}
