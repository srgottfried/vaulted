package com.vaultapp.view.cli;

import com.vaultapp.model.entities.Book;
import com.vaultapp.model.entities.BookVault;
import com.vaultapp.model.entities.Film;
import com.vaultapp.model.entities.FilmVault;
import java.util.List;
import java.util.stream.Stream;

public class CommandControllerView {

    public static final String COMMAND_NOT_FOUND = "Command not found.\n";
    public static final String LOGIN_ERROR = "Incorrect user or password. Please, try again.\n";
    public static final String NO_SESION = "You must login before.\n";
    public static final String VAULT_ALREADY_EXISTS = "This vault already exists. Try another name.\n";
    public static final String VAULT_NOT_FOUND = "Vault not found. Try another vault name.\n";
    public static final String BOOK_NOT_FOUND = "Book not found. Please, try again.\n";
    public static final String FILM_NOT_FOUND = "Film not found. Please, try again.\n";
    public static final String INVALID_BOOK_FILTER = "Invalid filter. Valid filters are: `author`, `title` or `isbn`.\n";
    public static final String INVALID_FILM_FILTER = "Invalid filter. Valid filters are: `title` or `tmid`.\n";
    public static final String BOOK_ALREADY_EXISTS = "Book already exists in the vault.\n";
    public static final String FILM_ALREADY_EXISTS = "Film already exists in the vault.\n";
    public static final String SUCCESSFULLY_ACTION = "Action done successfully.\n";
    public static final String INVALID_ACTION = "Invalid action. Try again.";
    public static final String INVALID_INPUT_ARGUMENTS = "Invalid input arguments";
    public static final String LOGOUT = "Bye %s. Your session is closed. See you soon.\n";
    public static final String REMOVE_BOOK = "\n" + "Book has been successfully deleted.";
    public static final String REMOVE_FILM = "\n" + "Film has been successfully deleted.";
    public static final String HELP =
            "THEME\n" +
                    "\tVault CLI Help System\n" +
                    "\n" +
                    "DESCRIPTION\n" +
                    "\tDisplays help about Vault CLI commands and concepts.\n" +
                    "\n" +
                    "COMMANDS\n" +
                    "\n\tGENERAL COMMANDS\n" +
                    "\t\tlogin [-u|--user] USER [-p|--password] PASSWORD\n" +
                    "\t\t\tLogin with your Vaulted account. Use your USER and your PASSWORD." +
                    "\n\n" +
                    "\t\tlogout\n" +
                    "\t\t\tClose your session." +
                    "\n\n" +
                    "\t\tstatus\n" +
                    "\t\t\tDescription of the session information, vaults and content relative your profile." +
                    "\n\n" +
                    "\t\texit\n" +
                    "\t\t\tClose your sesion, then the application." +
                    "\n\n" +
                    "\n\tVAULT MANAGE COMMANDS\n" +
                    "\t\tcreate [-bv|-fv|--bookvault|--filmvault] NAME\n" +
                    "\t\t\tCreate a bookvault or filmvault to storage inner your film or book collections." +
                    "\n\n" +
                    "\t\topen [-bv|-fv|--bookvault|--filmvault] NAME\n" +
                    "\t\t\tOpen a bookvault or filmvault by NAME." +
                    "\n\n" +
                    "\t\tdelete [-bv|-fv|--bookvault|--filmvault] NAME\n" +
                    "\t\t\tDelete a bookvault or filmvault by NAME" +
                    "\n\n" +
                    "\n\tBOOKS/FILMS MANAGE COMMANDS\n" +
                    "\t\tsearch [-b|-f|--book|--film] NAME\n" +
                    "\t\t\tSearch by NAME books or films in main database. If an item does not exist, browser uses differents APIs to load information." +
                    "\n\n" +
                    "\t\tadd [-b|--book] --isbn ISBN [-v|--vault] VAULT_NAME\n" +
                    "\t\t\tAdd book into a specific bookvault VAULT_NAME by ISBN." +
                    "\n\n" +
                    "\t\tfind [-bv|-fv|--bookvault|--filmvault] VAULT_NAME [-sf|--filter] FILTER [-a|--arg] ARGUMENT\n" +
                    "\t\t\tFind book or film into a vault with a filter." +
                    "\n\n" +
                    "\t\tchsts --isbn ISBN [-v|--vault] VAULT_NAME\n" +
                    "\t\t\tChange the status of a book." +
                    "\n\n" +
                    "\t\tdelete [-b|--book] --isbn ISBN [-v|--vault] VAULT_NAME\n" +
                    "\t\t\tDelete an existing element in VAULT_NAME by ISBN." +
                    "\n\n";

    private final String[] PROMPT = {"", "> "};
    private final String WELCOME = "Welcome %s.\n";
    private final String STATUS =
            "\n===================\n" +
                    "Username: %s\n" +
                    "Last connection: %s\n" +
                    "-------------------\n" +
                    "BookVaults: %s\n" +
                    "FilmVaults: %s\n";

    public void removeFilmView() {
        System.out.println(REMOVE_FILM);
    }

    public void welcomeView(String arg) {
        System.out.println(String.format(WELCOME, arg));
    }

    public void bookAlreadyExistsView() {
        System.out.println(BOOK_ALREADY_EXISTS);
    }

    public void filmAlreadyExistsView() {
        System.out.println(FILM_ALREADY_EXISTS);
    }

    public void invalidInputArgumentsView() {
        System.out.println(INVALID_INPUT_ARGUMENTS);
    }

    public void logoutView(String arg) {
        System.out.println(String.format(LOGOUT, arg));
    }

    public void removeBookView() {
        System.out.println(REMOVE_BOOK);
    }

    public void invalidBookFilter() {
        System.out.println(INVALID_BOOK_FILTER);
    }

    public void invalidFilmFilter() {
        System.out.println(INVALID_FILM_FILTER);
    }

    public void invalidActionView() {
        System.out.println(INVALID_ACTION);
    }

    public void modifyPrompt(String arg) {
        PROMPT[0] = "session-" + arg;
    }

    public void printPrompt() {
        System.out.printf(String.join("", PROMPT));
    }

    public void resetPrompt() {
        PROMPT[0] = "";
    }

    public void commandNotFoundView() {
        System.out.println(COMMAND_NOT_FOUND);
    }

    public void loginErrorView() {
        System.out.println(LOGIN_ERROR);
    }

    public void statusView(String name, String lastConnection, List<BookVault> bookVaults, List<FilmVault> filmVaults) {
        System.out.println(String.format(STATUS, name, lastConnection, bookVaults, filmVaults));
    }

    public void vaultAlreadyExistsView() {
        System.out.println(VAULT_ALREADY_EXISTS);
    }

    public void successfullyActionView() {
        System.out.println(SUCCESSFULLY_ACTION);
    }

    public void noSesionView() {
        System.out.println(NO_SESION);
    }

    public void bookNotFoundView() {
        System.out.println(BOOK_NOT_FOUND);
    }

    public void filmNotFoundView() {
        System.out.println(FILM_NOT_FOUND);
    }

    public void vaultNotFoundView() {
        System.out.println(VAULT_NOT_FOUND);
    }


    public void listOfSearchedBooksView(List<Book> books) {
        String view = "=============\n" +
                "Title: %s\n" +
                "Author: %s\n" +
                "ISBN: %s\n" +
                "Publish Year: %s\n" +
                "Cover: %s\n";
        for (Book b : books) {
            System.out.println(String.format(view, b.getTitle(), b.getAuthor(), b.getIsbn(), b.getPublishYear(), b.getCover()));
        }
    }

    public void listOfBooksView(List<Book> books) {
        String view = "=============\n" +
                "Title: %s\n" +
                "Author: %s\n" +
                "ISBN: %s\n" +
                "Publish Year: %s\n" +
                "Cover: %s\n" +
                "Status: %s\n";
        for (Book b : books) {
            String status;
            status = b.isStatus() ? "Finish" : "To read";
            System.out.println(String.format(view, b.getTitle(), b.getAuthor(), b.getIsbn(), b.getPublishYear(), b.getCover(), status));
        }
    }

    public void listOfSearchedFilmsView(List<Film> films) {
        String view = "=============\n" +
                "Title: %s\n" +
                "Genres: %s\n" +
                "Id: %s\n" +
                "Publish Year: %s\n" +
                "Cover: %s\n";
        for (Film f : films) {
            System.out.println(String.format(view, f.getTitle(), f.getGenres(), f.getTmdbId(), f.getReleaseDate(), f.getPosterPath()));
        }
    }

    public void listOfFilmsView(List<Film> films) {
        String view = "=============\n" +
                "Title: %s\n" +
                "Genres: %s\n" +
                "Id: %s\n" +
                "Publish Year: %s\n" +
                "Cover: %s\n" +
                "Status: %s\n";
        for (Film f : films) {
            String status;
            status = f.isStatus() ? "Finish" : "To watch";
            System.out.println(String.format(view, f.getTitle(), f.getGenres(), f.getTmdbId(), f.getReleaseDate(), f.getPosterPath(), status));
        }
    }

    public void helpView() {
        System.out.println(HELP);
    }


}
