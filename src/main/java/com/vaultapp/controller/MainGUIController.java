package com.vaultapp.controller;

import com.vaultapp.MainGUI;
import com.vaultapp.login.UserSession;
import com.vaultapp.model.entities.*;
import com.vaultapp.model.repository.UserRepository;
import com.vaultapp.model.entities.Film;
import com.vaultapp.model.entities.VaultItem;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static atlantafx.base.theme.Styles.*;

/**
 * The MainGUIController is a controller class for the main GUI view. It allows users to view and manage
 * their books and films in a selected vault.
 *
 */
public class MainGUIController {

    /**
     * Button that opens and closes the sidebar menu.
     */
    @FXML
    public Button btnSideMenu;

    /**
     * Button that displays the book vault view.
     */
    @FXML
    public Button btnBookView;

    /**
     * Button that displays the film vault view.
     */
    @FXML
    public Button btnFilmView;

    /**
     * VBox that holds the sidebar menu items.
     */
    @FXML
    public VBox vbxSidebar;

    /**
     * VBox that holds the vault controls.
     */
    @FXML
    public VBox vaultControlsContainer;
    /**
     * TableView that displays the items in the selected vault.
     */
    @FXML
    public TableView<VaultItem> tblItems;

    /**
     * Spacer that separates the sidebar menu and the main content.
     */
    @FXML
    public Region spacer;

    /**
     * Label that displays the title of the selected vault.
     */
    @FXML
    public Label title;

    /**
     * Spacer that separates the title label and the main content.
     */
    @FXML
    public Region titleSpacer;

    /**
     * Rectangle that forms a layer over the main content.
     */
    public Rectangle layer;

    /**
     * BorderPane that holds the controls for the selected item.
     */
    @FXML
    public BorderPane controlsLayer;

    /**
     * BorderPane that holds the main content.
     */
    @FXML
    public BorderPane userContent;

    /**
     * ImageView that displays the image of the selected item.
     */
    @FXML
    public ImageView itemImage;

    /**
     * Label that displays the first detail field of the selected item.
     */
    @FXML
    public Label detailField1;

    /**
     * Label that displays the second detail field of the selected item.
     */
    @FXML
    public Label detailField2;

    /**
     * Label that displays the third detail field of the selected item.
     */
    @FXML
    public Label detailField3;

    /**
     * Label that displays the fourth detail field of the selected item.
     */
    @FXML
    public Label detailField4;

    /**
     * VBox that holds the detail view of the selected item.
     */
    @FXML
    public VBox detailView;

    /**
     * VBox that holds the information of the selected item.
     */
    @FXML
    public VBox itemInfo;

    /**
     * Rectangle that forms a layer over the controlsLayer.
     */
    @FXML
    public Rectangle topLayer;

    /**
     * Button that opens the add item view.
     */
    @FXML
    public Button btnAddView;

    /**
     * Button that logs the user out and returns to the login view.
     */
    @FXML
    public Button btnLogout;

    /**
     * ChoiceBox that allows the user to change the status of the selected item.
     */
    @FXML
    public ChoiceBox<String> chbStatus;

    /**
     * Interpolator for smoothing animations.
     */
    private final Interpolator slide = Interpolator.SPLINE(0, 0, 0.1, 1);
    /**
     * Button for deleting items from a vault.
     */
    @FXML
    public Button btnDelete;
    @FXML
    private Rectangle rect;
    /**
     * String that defines the border style for the sidebar when it is open.
     */
    private final String SIDEBAR_BORDER = "-fx-border-style: hidden solid hidden hidden";
    /**
     * String that defines the border style for the sidebar when it is closed.
     */
    private final String HIDDEN_BORDER = "-fx-border-style: hidden";
    /**
     * Double that stores the default width of the sidebar menu buttons.
     */
    private double btnDefWidth;
    /**
     * Double that stores the expanded width of the sidebar menu buttons.
     */
    private double btnExpandedWidth = 180d;
    /**
     * Boolean that stores the state of the sidebar menu (open or closed).
     */
    private boolean expanded = false;
    /**
     * Boolean that stores the state of the selected vault view (film or book).
     */
    private static boolean filmsSelected = true;
    /**
     * Vault object that stores the currently selected vault.
     */
    private static Vault selectedVault;
    /**
     * UserSession object that stores the current user session.
     */
    private UserSession session = UserSession.getInstance();

    /**
     * Returns de state of the selected vault view.
     * @return state of the selected vault view (true for films, false for books)
     */
    public static boolean isFilmsSelected() {
        return filmsSelected;
    }

    /**
     * Sets the currently selected vault.
     * @param selectedVault vault to select
     */
    public static void setSelectedVault(Vault selectedVault) {
        MainGUIController.selectedVault = selectedVault;
    }

    /**
     * Returns the currently selected vault.
     * @return currently selected vault
     */
    public static Vault getSelectedVault() {
        return selectedVault;
    }

    /**
     * Toggles the state of the sidebar menu.
     * @param actionEvent the event that triggered the method
     */
    public void expandRectangle(ActionEvent actionEvent) {
        if (!expanded) {
            btnFilmView.textAlignmentProperty().setValue(TextAlignment.LEFT);
            btnBookView.textAlignmentProperty().setValue(TextAlignment.LEFT);
            btnBookView.setGraphic(new ImageView(new Image(Objects.requireNonNull(MainGUI.class.getResource("book.png")).toString())));
            btnAddView.textAlignmentProperty().setValue(TextAlignment.LEFT);
            btnLogout.textAlignmentProperty().setValue(TextAlignment.LEFT);
            vaultControlsContainer.setAlignment(Pos.CENTER_LEFT);
            vbxSidebar.requestFocus();
            vbxSidebar.setStyle("-fx-fill: #3c536e");
            vbxSidebar.setStyle(HIDDEN_BORDER);
            btnSideMenu.getStyleClass().add(ACCENT);
            btnFilmView.getStyleClass().add(ACCENT);
            btnBookView.getStyleClass().add(ACCENT);
            btnAddView.getStyleClass().add(ACCENT);
            btnLogout.getStyleClass().add(ACCENT);
            rect.setVisible(true);

            Timeline timeline = new Timeline();
            timeline.setCycleCount(1);
            KeyValue kv = new KeyValue(rect.widthProperty(), 220, slide);
            KeyValue kv2 = new KeyValue(rect.opacityProperty(), 1, slide);
            KeyValue filmBtnGrow = new KeyValue(btnFilmView.minWidthProperty(), 207, slide);
            KeyValue bookBtnGrow = new KeyValue(btnBookView.minWidthProperty(), 170, slide);
            KeyValue addBtnGrow = new KeyValue(btnAddView.minWidthProperty(), 170, slide);
            KeyValue logoutBtnGrow = new KeyValue(btnLogout.minWidthProperty(), 170, slide);
            KeyValue layerOpacity = new KeyValue(layer.opacityProperty(), 0.7);
            KeyFrame kf = new KeyFrame(Duration.millis(150d), kv, kv2, filmBtnGrow, bookBtnGrow, addBtnGrow, layerOpacity, logoutBtnGrow);
            timeline.setOnFinished(e -> {
                btnFilmView.setText("select film vault");
                btnBookView.setText("select book vault");
                btnAddView.setText("add item to vault");
                btnLogout.setText("log out");
                btnFilmView.maxWidthProperty().setValue(btnExpandedWidth);
                //btnFilmView.setPadding(expandedPadding);
            });
            timeline.getKeyFrames().add(kf);
            timeline.play();
            expanded = true;

        } else {
            btnFilmView.textAlignmentProperty().setValue(TextAlignment.CENTER);
            btnBookView.textAlignmentProperty().setValue(TextAlignment.CENTER);
            btnAddView.textAlignmentProperty().setValue(TextAlignment.CENTER);
            btnLogout.textAlignmentProperty().setValue(TextAlignment.CENTER);
            vaultControlsContainer.setAlignment(Pos.CENTER);
            btnFilmView.setText("");
            btnBookView.setText("");
            btnAddView.setText("");
            btnLogout.setText("");
            vbxSidebar.setStyle("-fx-fill: rgba(0,0,0,0)");
            btnSideMenu.getStyleClass().remove(ACCENT);
            btnBookView.getStyleClass().remove(ACCENT);
            btnFilmView.getStyleClass().remove(ACCENT);
            btnAddView.getStyleClass().remove(ACCENT);
            btnLogout.getStyleClass().remove(ACCENT);
            vbxSidebar.setStyle(SIDEBAR_BORDER);
            Timeline timeline = new Timeline();
            timeline.setCycleCount(1);
            KeyValue kv = new KeyValue(rect.widthProperty(), 0, slide);
            KeyValue kv2 = new KeyValue(rect.opacityProperty(), 0, slide);
            KeyValue filmBtnShrink = new KeyValue(btnFilmView.minWidthProperty(), btnDefWidth);
            KeyValue bookBtnShrink = new KeyValue(btnBookView.minWidthProperty(), btnDefWidth);
            KeyValue addBtnShrink = new KeyValue(btnAddView.minWidthProperty(), btnDefWidth);
            KeyValue logoutBtnShrink = new KeyValue(btnLogout.minWidthProperty(), btnDefWidth);
            KeyValue layerOpacity = new KeyValue(layer.opacityProperty(), 0d);
            KeyFrame kf = new KeyFrame(Duration.millis(150.0), kv, kv2, filmBtnShrink, bookBtnShrink, layerOpacity, addBtnShrink, logoutBtnShrink);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(e -> {
                rect.setVisible(false);
            });
            timeline.play();
            expanded = false;
        }

    }

    /**
     * Initializes the UI components of the main view.
     */
    public void initialize() {
        selectedVault = null;

        // Control flow
        EventHandler<MouseEvent> enableControls = e -> controlsLayer.setMouseTransparent(false);
        EventHandler<MouseEvent> disableControls = e -> controlsLayer.setMouseTransparent(true);
        detailView.setOnMouseEntered(disableControls);
        vbxSidebar.setOnMouseExited(disableControls);
        tblItems.setOnMouseExited(enableControls);
        title.setOnMouseEntered(disableControls);
        title.setOnMouseExited(enableControls);

        // Element events
        chbStatus.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            VaultItem item = tblItems.getSelectionModel().getSelectedItem();

            if (item instanceof Book) {
                Book b = (Book) item;
                b.setStatus(newValue.intValue() == 1);
                UserRepository.getInstance().add(session.getLoggedUser());
            } else {
                Film f = (Film) item;
                f.setStatus(newValue.intValue() == 1);
                UserRepository.getInstance().add(session.getLoggedUser());
            }
        });

        // Element initialization
        title.minWidthProperty().bind(tblItems.widthProperty().subtract(4));
        btnDelete.minWidthProperty().bind(chbStatus.widthProperty());
        btnDelete.setGraphic(new ImageView(new Image(Objects.requireNonNull(MainGUI.class.getResource("trash.png")).toString())));
        chbStatus.setItems(FXCollections.observableArrayList("Planning", "Completed"));
        vbxSidebar.setPadding(new Insets(0, 3, 0, 2));
        btnSideMenu.setGraphic(new ImageView(new Image(Objects.requireNonNull(MainGUI.class.getResource("list.png")).toString())));
        btnSideMenu.getStyleClass().add(LARGE);
        btnBookView.setGraphic(new ImageView(new Image(Objects.requireNonNull(MainGUI.class.getResource("book.png")).toString())));
        btnBookView.getStyleClass().addAll(FONT_ICON, LARGE);
        btnFilmView.setGraphic(new ImageView(new Image(Objects.requireNonNull(MainGUI.class.getResource("film.png")).toString())));
        btnFilmView.getStyleClass().addAll(FONT_ICON, LARGE);
        btnAddView.setGraphic(new ImageView(new Image(Objects.requireNonNull(MainGUI.class.getResource("plus.png")).toString())));
        btnAddView.getStyleClass().addAll(FONT_ICON, LARGE);
        btnLogout.setGraphic(new ImageView(new Image(Objects.requireNonNull(MainGUI.class.getResource("person.png")).toString())));

        btnDefWidth = btnFilmView.getWidth();
        spacer.minWidthProperty().setValue(58);
        titleSpacer.minWidthProperty().setValue(62);
        tblItems.getStyleClass().add(STRIPED);
        tblItems.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        detailView.setPadding(new Insets(5d));
        detailView.setMaxWidth(300d);
        itemImage.setFitHeight(300);
        itemImage.setFitWidth(200);
        itemInfo.autosize();

    }

    /**
     * Opens the Choose Vault Dialog and sets the selected vault as the current vault.
     * @param actionEvent ActionEvent object that triggered the method
     * @throws IOException if there is an error loading the FXML file for the Choose Vault Dialog
     */
    public void chooseVault(ActionEvent actionEvent) throws IOException {

        darkenAll();

        List<Vault> vaults;

        if (actionEvent.getSource().equals(btnBookView)) {

            vaults = new ArrayList<>(session.getLoggedUser().getBookVaults());
            ChooseVaultDialogController.setChoosingFilms(false);

        } else {

            vaults = new ArrayList<>(session.getLoggedUser().getFilmVaults());
            ChooseVaultDialogController.setChoosingFilms(true);

        }

        Vault oldVault = selectedVault;

        ChooseVaultDialogController.setVaultList(vaults);
        launchDialog("chooseVaultDialog-view.fxml", "Choose a vault");

        lightenAll();

        if (selectedVault == null) {

            tblItems.getItems().clear();
            title.setText("No vault selected");
            return;

        }

        if (oldVault == null || !oldVault.equals(selectedVault)) {

            if (selectedVault instanceof BookVault) {

                swapToBookVault();

            } else {

                swapToFilmVault();

            }

        }

    }

    /**
     * Returns the whole main window to its initial color.
     */
    private void lightenAll() {

        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        KeyValue kv = new KeyValue(topLayer.opacityProperty(), 0.0, slide);
        KeyFrame kf = new KeyFrame(Duration.millis(150d), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();

    }

    /**
     * Darkens the whole main window.
     */
    private void darkenAll() {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        KeyValue kv = new KeyValue(topLayer.opacityProperty(), 0.7, slide);
        KeyFrame kf = new KeyFrame(Duration.millis(150d), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    /**
     * Switches the current vault view to the film vault.
     */
    private void swapToFilmVault() {

        clearDetailView();
        tblItems.getItems().clear();
        tblItems.getColumns().clear();
        FilmVault vault = (FilmVault) selectedVault;
        title.setText(session.getLoggedUser().getName() + "'s film vault: " + selectedVault.getName());
        filmsSelected = true;

        ObservableList<VaultItem> films = FXCollections.observableArrayList(vault.getFilms());
        tblItems.setItems(films);
        TableColumn<VaultItem, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<VaultItem, LocalDate> releaseCol = new TableColumn<>("Release");
        releaseCol.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        tblItems.getColumns().addAll(titleCol, releaseCol);
        tblItems.refresh();

    }

    /**
     * Resets the detail view to its initial state.
     */
    private void clearDetailView() {
        chbStatus.setVisible(false);
        btnDelete.setVisible(false);

        itemImage.setImage(null);
        Stream.of(detailField1, detailField2, detailField3, detailField4)
                .forEach(field -> field.setText(""));

    }

    /**
     * Switches the current vault view to the book vault.
     */
    private void swapToBookVault() {

        tblItems.getItems().clear();
        tblItems.getColumns().clear();
        BookVault vault = (BookVault) selectedVault;
        title.setText(session.getLoggedUser().getName() + "'s book vault: " + selectedVault.getName());
        filmsSelected = false;

        ObservableList<VaultItem> books = FXCollections.observableArrayList(vault.getBooks());
        tblItems.setItems(books);
        TableColumn<VaultItem, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<VaultItem, String> authorCol = new TableColumn<>("Author");
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        TableColumn<VaultItem, String> releaseCol = new TableColumn<>("Release");
        releaseCol.setCellValueFactory(new PropertyValueFactory<>("publishYear"));;
        tblItems.getColumns().addAll(titleCol, authorCol, releaseCol);
        tblItems.refresh();

    }

    /**
     * It takes the selected item from the table and displays its details in the detail view
     *
     * @param mouseEvent The mouse event that triggered the method.
     */
    public void refreshDetailView(MouseEvent mouseEvent) {

        if (selectedVault == null)
            return;

        chbStatus.setVisible(true);
        btnDelete.setVisible(true);

        if (filmsSelected) {

            Film f = (Film) tblItems.getSelectionModel().getSelectedItem();

            String posterPath = f.getPosterPath();

            Image image = posterPath != null ?
                    new Image(posterPath) :
                    new Image(Objects.requireNonNull(MainGUI.class.getResource("placeholder.png")).toString());

            itemImage.setImage(image);

            detailField1.setText("Title: " + f.getTitle());
            detailField2.setText("Genres: " + String.join(", ", f.getGenres()));
            detailField3.setText("Overview: " + f.getOverview());
            detailField4.setText("Tagline: " + f.getTagline());
            chbStatus.getSelectionModel().select(f.isStatus() ? 1 : 0);

        } else {

            Book b = (Book) tblItems.getSelectionModel().getSelectedItem();

            String cover = b.getCover().toString();
            Image image = cover != null ?
                    new Image(cover) :
                    new Image(Objects.requireNonNull(MainGUI.class.getResource("placeholder.png")).toString());

            if (image.isError()) {
                image = new Image(Objects.requireNonNull(MainGUI.class.getResource("placeholder.png")).toString());
            }

            itemImage.setImage(image);

            detailField1.setText("Title: " + b.getTitle());
            detailField2.setText("Author: " + b.getAuthor());
            detailField3.setText("Publish date: " + b.getPublishYear());
            detailField4.setText("ISBN: " + b.getIsbn());
            chbStatus.getSelectionModel().select(b.isStatus() ? 1 : 0);

        }

    }

    /**
     * Shows the add item view.
     * @param actionEvent MouseEvent object that triggered the method
     * @throws IOException if there is an error loading the FXML file for the add item view
     */
    public void onAddItemClick(ActionEvent actionEvent) throws IOException {

        if (selectedVault == null) {

            darkenAll();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot add item");
            alert.setContentText("No vault selected");
            alert.showAndWait();

            lightenAll();
            return;

        }

        darkenAll();
        launchDialog("elementAdd-view.fxml", filmsSelected ? "Add a film" : "Add a book");
        lightenAll();

        if (expanded) {
            expandRectangle(new ActionEvent());
        }

        if (filmsSelected) {

            FilmVault vault = (FilmVault) selectedVault;
            tblItems.setItems(FXCollections.observableArrayList(vault.getFilms()));

        } else {

            BookVault vault = (BookVault) selectedVault;
            tblItems.setItems(FXCollections.observableArrayList(vault.getBooks()));

        }

    }

    /**
     * Shows a dialog with a given and title.
     * @param resource dialog view to be loaded
     * @param title the title of the dialog
     */
    private void launchDialog(String resource, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource(resource));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.initOwner(MainGUI.getMainStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * Logs the user out and shows the login view.
     * @param actionEvent object that triggered the method
     */
    public void onLogoutClick(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log out");
        alert.setHeaderText("Logging out");
        alert.setContentText("Your data will be saved");

        darkenAll();

        Optional<ButtonType> result = alert.showAndWait();

        lightenAll();

        if (result.isEmpty() || result.get().equals(ButtonType.CANCEL)) {
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("login-view.fxml"));
        Stage current = (Stage) btnLogout.getScene().getWindow();

        try {
            current.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        session.logout();
    }

    /**
     * Deletes the selected item from the table and updates the table
     *
     * @param actionEvent The event that triggered the action.
     */
    public void onDeleteClick(ActionEvent actionEvent) {

        VaultItem item = tblItems.getSelectionModel().getSelectedItem();

        Vault vault = session.getLoggedUser().findVaultByName(selectedVault.getName()).get(0);

        vault.deleteElement(item);
        selectedVault = vault;

        if (vault instanceof BookVault) {

            BookVault bookVault = (BookVault) vault;
            tblItems.setItems(FXCollections.observableArrayList(bookVault.getBooks()));

        } else {

            FilmVault filmVault = (FilmVault) vault;
            tblItems.setItems(FXCollections.observableArrayList(filmVault.getFilms()));

        }

        UserRepository.getInstance().add(session.getLoggedUser());
        clearDetailView();

    }
}
