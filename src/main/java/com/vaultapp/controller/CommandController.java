package com.vaultapp.controller;


import com.vaultapp.login.UserSession;
import com.vaultapp.model.entities.*;
import com.vaultapp.model.repository.*;
import com.vaultapp.utilities.filters.BookFilter;
import com.vaultapp.utilities.filters.FilmFilter;
import com.vaultapp.view.cli.CommandControllerView;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class CommandController {
    private CommandControllerView view;
    private final Scanner sc;
    private boolean exit;

    private final Set<String> reservedWords = Set.of(
            "login",
            "logout",
            "status",
            "create",
            "open",
            "delete",
            "search",
            "find",
            "add",
            "exit",
            "chsts",
            "--user",
            "-u",
            "--gift",
            "-g",
            "--password",
            "-p",
            "--book",
            "-b",
            "--film",
            "-f",
            "--bookvault",
            "-bv",
            "--filmvault",
            "-fv",
            "--name",
            "--title",
            "--isbn",
            "--tmid",
            "--vault",
            "-v",
            "--filter",
            "-ff",
            "--arg",
            "-a",
            "-n",
            "-d",
            "help"
    );

    public CommandController() {
        this.view = new CommandControllerView();
        this.sc = new Scanner(System.in);
        exit = false;
        this.commandLoop();
    }

    /**
     * Processes user input in a loop until the `exit` flag is set to `true`.
     * This method takes user input from the console, parses it using the `parseUserInput` method,
     * and then passes the result to the `processParsedCommand` method for execution.
     */
    private void commandLoop() {
        do {
            view.printPrompt();
            String input = sc.nextLine();
            List<String> parseInput = parser(input);
            if (parseInput != null) {
                processParserCommand(parseInput);
            }
        } while (!exit);
    }

    /**
     * Parse input command to get the list structure of
     * [action,args...] where action defines the method to use.
     *
     * @param lineCommand
     * @return List structured with all input information or null if commands not found.
     */
    private List<String> parser(String lineCommand) {
        String action;
        List<String> args = new LinkedList<>();
        List<String> noArgs = new ArrayList<>();
        String[] splitLineCommand = lineCommand.split(" ");

        noArgs.add(splitLineCommand[0]);
        Arrays.stream(splitLineCommand).filter(s -> s.startsWith("-")).forEach(noArgs::add);
        if (!reservedWords.containsAll(noArgs)) {
            this.view.commandNotFoundView();
            return null;
        }
        List<Integer> index = new ArrayList<>();
        for (int i = 0; i < splitLineCommand.length; i++) {
            if (splitLineCommand[i].startsWith("-")) {
                index.add(i);
            }
        }
        StringBuilder composeArgs = new StringBuilder();
        for (int i : index) {
            if (splitLineCommand.length <= i + 1) {
                break;
            } else {
                for (int j = i + 1; j < splitLineCommand.length; j++) {
                    if (splitLineCommand[j].startsWith("-")) {
                        break;
                    }
                    composeArgs.append(splitLineCommand[j]).append(" ");
                }
                if (!composeArgs.toString().isBlank() && !composeArgs.toString().isEmpty()) {
                    args.add(composeArgs.toString().strip());
                    composeArgs.setLength(0);
                }
            }
        }
        //Arrays.stream(splitLineCommand).filter(s -> !noArgs.contains(s)).forEach(args::add);
        action = String.join("", noArgs);
        args.add(0, action);
        return args;
    }

    private void processParserCommand(List<String> parserCommand) {
        switch (parserCommand.get(0)) {
            case "exit":
                actionExit();
                return;
            case "login--user--password":
            case "login-u-p":
                try {
                    actionLogin(parserCommand.get(1), parserCommand.get(2));
                } catch (Exception e) {
                    view.invalidInputArgumentsView();
                } finally {
                    return;
                }
            case "help":
                actionHelp();
                return;
        }
        // Session is needed active to work
        if (!UserSession.getInstance().inSession()) {
            view.noSesionView();
        } else {
            switch (parserCommand.get(0)) {
                case "logout":
                    try {
                        actionLogout();
                    } catch (Exception e) {
                        view.invalidInputArgumentsView();
                    } finally {
                        break;
                    }
                case "status":
                    try {
                        actionStatusUser();
                    } catch (Exception e) {
                        view.invalidInputArgumentsView();
                    } finally {
                        break;
                    }
                case "create--bookvault":
                case "create-bv":
                    try {
                        actionCreateBookVault(parserCommand.get(1));
                    } catch (Exception e) {
                        view.invalidInputArgumentsView();
                    } finally {
                        break;
                    }
                case "create--filmvault":
                case "create-fv":
                    try {
                        actionCreateFilmVault(parserCommand.get(1));
                    } catch (Exception e) {
                        view.invalidInputArgumentsView();
                    } finally {
                        break;
                    }
                case "add--gift--name--description":
                case "add-g-n-d":
                    try {
                        actionAddGift(parserCommand.get(1), parserCommand.get(2));
                    } catch (Exception e) {
                        view.invalidInputArgumentsView();
                    } finally {
                        break;
                    }
                case "open--bookvault":
                case "open-bv":
                    try {
                        actionOpenBookVault(parserCommand.get(1));
                    } catch (Exception e) {
                        view.invalidInputArgumentsView();
                    } finally {
                        break;
                    }
                case "find--bookvault--filter--arg":
                case "find-bv-ff-a":
                    try {
                        actionFindBookByFilterInBookVault(parserCommand.get(1), parserCommand.get(2), parserCommand.get(3));
                    } catch (Exception e) {
                        view.invalidInputArgumentsView();
                    } finally {
                        break;
                    }
                case "find--filmvault--filter--arg":
                case "find-fv-ff-a":
                    try {
                        actionFindFilmByFilterInFilmVault(parserCommand.get(1), parserCommand.get(2), parserCommand.get(3));
                    } catch (Exception e) {
                        view.invalidInputArgumentsView();
                    } finally {
                        break;
                    }
                case "open--filmvault":
                case "open-fv":
                    try {
                        actionOpenFilmVault(parserCommand.get(1));
                    } catch (Exception e) {
                        view.invalidInputArgumentsView();
                    } finally {
                        break;
                    }
                case "delete--bookvault":
                case "delete-bv":
                    try {
                        actionDeleteBookVault(parserCommand.get(1));
                    } catch (Exception e) {
                        view.invalidInputArgumentsView();
                    } finally {
                        break;
                    }
                case "delete--filmvault":
                case "delete-fv":
                    try {
                        actionDeleteFilmVault(parserCommand.get(1));
                    } catch (Exception e) {
                        view.invalidInputArgumentsView();
                    } finally {
                        break;
                    }
                case "search--book":
                case "search-b":
                    try {
                        actionSearchBookTitle(parserCommand.get(1));
                    } catch (Exception e) {
                        view.invalidInputArgumentsView();
                    } finally {
                        break;
                    }
                case "search--film":
                case "search-f":
                    try {
                        actionSearchFilmTitle(parserCommand.get(1));
                    } catch (Exception e) {
                        view.invalidInputArgumentsView();
                    } finally {
                        break;
                    }
                case "chsts--isbn--vault":
                case "chsts--isbn-v":
                    try {
                        actionChangeBookStatus(parserCommand.get(1), parserCommand.get(2));
                    } catch (Exception e) {
                        view.invalidInputArgumentsView();
                    } finally {
                        break;
                    }
                case "chsts--tmid--vault":
                case "chsts--tmid-v":
                    try {
                        actionChangeFilmStatus(parserCommand.get(1), parserCommand.get(2));
                    } catch (Exception e) {
                        view.invalidInputArgumentsView();
                    } finally {
                        break;
                    }
                case "add--book--isbn--vault":
                case "add-b--isbn-v":
                    try {
                        actionAddBook(parserCommand.get(1), parserCommand.get(2));
                    } catch (Exception e) {
                        view.invalidInputArgumentsView();
                    } finally {
                        break;
                    }
                case "add--film--tmid--vault":
                case "add-f--tmid-v":
                    try {
                        actionAddFilm(parserCommand.get(1), parserCommand.get(2));
                    } catch (Exception e) {
                        view.invalidInputArgumentsView();
                    } finally {
                        break;
                    }
                case "delete--book--isbn--vault":
                case "delete-b--isbn-v":
                    try {
                        actionDeleteBook(parserCommand.get(1), parserCommand.get(2));
                    } catch (Exception e) {
                        view.invalidInputArgumentsView();
                    } finally {
                        break;
                    }
                case "delete--film--isbn--vault":
                case "delete-f--tmid-v":
                    try {
                        actionDeleteFilm(parserCommand.get(1), parserCommand.get(2));
                    } catch (Exception e) {
                        view.invalidInputArgumentsView();
                    } finally {
                        break;
                    }
                default:
                    view.commandNotFoundView();
            }
        }
    }

    private void actionAddGift(String giftName, String giftDescription) {
        User u = UserSession.getInstance().getLoggedUser();
        try {
            Regalo r = new Regalo(giftName, giftDescription);
            u.addRegalo(r);
            UserRepository.getInstance().add(u);
            view.successfullyActionView();
        } catch (Exception e) {
            view.invalidActionView();
        }

    }


    private void actionFindFilmByFilterInFilmVault(String vaultName, String filter, String arg) {
        User u = UserSession.getInstance().getLoggedUser();
        List<FilmVault> vaults = u.getFilmVaults().stream().filter(fv -> fv.getName().equals(vaultName)).collect(Collectors.toList());
        List<Film> films = new ArrayList<>();
        if (!vaults.isEmpty()) {
            switch (filter) {
                case "author":
                    films = FilmDbRepository.getInstance().findBy(u, FilmFilter.AUTHOR, arg);
                    break;
                case "tmid":
                    films = FilmDbRepository.getInstance().findBy(u, FilmFilter.TITLE, arg);
                    break;
                default:
                    view.invalidFilmFilter();
            }
            if (!films.isEmpty()) {
                view.listOfFilmsView(films);
            } else {
                view.filmNotFoundView();
            }

        } else {
            view.vaultNotFoundView();
        }
    }

    private void actionFindBookByFilterInBookVault(String vaultName, String filter, String arg) {
        User u = UserSession.getInstance().getLoggedUser();
        List<BookVault> vaults = u.getBookVaults().stream().filter(bv -> bv.getName().equals(vaultName)).collect(Collectors.toList());
        List<Book> books = new ArrayList<>();
        if (!vaults.isEmpty()) {
            switch (filter) {
                case "author":
                    books = BookDbRepository.getInstance().findBy(u, BookFilter.AUTHOR, arg);
                    break;
                case "title":
                    books = BookDbRepository.getInstance().findBy(u, BookFilter.TITLE, arg);
                    break;
                case "isbn":
                    books = BookDbRepository.getInstance().findBy(u, BookFilter.ISBN, arg);
                    break;
                default:
                    view.invalidBookFilter();
            }
            if (!books.isEmpty()) {
                view.listOfBooksView(books);
            } else {
                view.bookNotFoundView();
            }

        } else {
            view.vaultNotFoundView();
        }
    }


    private void actionChangeBookStatus(String isbn, String vaultName) {
        User u = UserSession.getInstance().getLoggedUser();
        var vault = u.getBookVaults().stream().filter(v -> v.getName().equals(vaultName)).collect(Collectors.toList());
        if (!vault.isEmpty()) {
            var books = vault.get(0).getBooks();
            if (!books.isEmpty()) {
                var book = books.stream().filter(b -> b.getIsbn().equals(isbn)).collect(Collectors.toList());
                if (!book.isEmpty()) {
                    book.get(0).changeStatus();
                    view.successfullyActionView();
                    UserRepository.getInstance().add(u);
                } else {
                    view.bookNotFoundView();
                }
            } else {
                view.bookNotFoundView();
            }
        } else {
            view.vaultNotFoundView();
        }
    }


    private void actionHelp() {
        view.helpView();
    }

    private void actionCreateBookVault(String name) {
        User u = UserSession.getInstance().getLoggedUser();
        BookVault v = new BookVault(name);
        if (!u.getBookVaults().contains(v)) {
            u.addVault(v);
            view.successfullyActionView();
        } else {
            view.vaultAlreadyExistsView();
        }
        //update user status
        UserRepository.getInstance().add(u);
    }

    private void actionCreateFilmVault(String name) {
        User u = UserSession.getInstance().getLoggedUser();
        FilmVault v = new FilmVault(name);
        if (!u.getFilmVaults().contains(v)) {
            u.addVault(v);
            view.successfullyActionView();
        } else {
            view.vaultAlreadyExistsView();
        }
        //update user status
        UserRepository.getInstance().add(u);
    }

    private void actionDeleteBook(String isbn, String vaultName) {
        User u = UserSession.getInstance().getLoggedUser();
        List<BookVault> bvs = u.getBookVaults();
        for (BookVault bv : bvs) {
            if (bv.getName().equals(vaultName)) {
                Book b = bv.findByIsbn(isbn);
                if (b != null) {
                    bv.deleteElement(b);
                    view.removeBookView();
                    UserRepository.getInstance().add(u);
                    return;
                }
                view.bookNotFoundView();
                return;
            }
            view.vaultNotFoundView();
            return;
        }
    }

    private void actionDeleteFilm(String tmid, String vaultName) {
        User u = UserSession.getInstance().getLoggedUser();
        List<FilmVault> fvs = u.getFilmVaults();
        for (FilmVault fv : fvs) {
            if (fv.getName().equals(vaultName)) {
                Film f = fv.findByTmid(tmid);
                if (f != null) {
                    fv.deleteElement(f);
                    view.removeFilmView();
                    UserRepository.getInstance().add(u);
                    return;
                }
                view.filmNotFoundView();
                return;
            }
            view.vaultNotFoundView();
            return;
        }
    }

    private void actionAddFilm(String tmid, String vaultName) {
        var vaultList = UserSession.getInstance().getLoggedUser().getFilmVaults().stream().filter(bv -> bv.getName().equals(vaultName)).collect(Collectors.toList());
        // test if vaultList exists
        if (vaultList.size() == 0) {
            view.vaultNotFoundView();
            return;
        }
        if (vaultList.get(0).findByTmid(tmid) != null) {
            view.filmAlreadyExistsView();
            return;
        }
        // test if isbn exists in db
        Film bdf = FilmDbRepository.getInstance().findByTmid(tmid);
        if (bdf == null) {
            // test if isbn exists in the api
            Film f = FilmApiRepository.getInstance().getByTmid(tmid);
            if (f == null) {
                view.filmNotFoundView();
                return;
            }
            vaultList.get(0).addElement(f);
        } else {
            vaultList.get(0).addElement(bdf);
        }
        view.successfullyActionView();
        //update user status
        UserRepository.getInstance().add(UserSession.getInstance().getLoggedUser());
    }

    /**
     * Changes the status of a film in the specified film vault belonging to the currently logged-in user.
     *
     * @param tmid      The TMDB ID of the film whose status should be changed.
     * @param vaultName The name of the film vault in which the film is located.
     */
    private void actionChangeFilmStatus(String tmid, String vaultName) {
        User u = UserSession.getInstance().getLoggedUser();
        var vault = u.getFilmVaults().stream().filter(v -> v.getName().equals(vaultName)).collect(Collectors.toList());
        if (!vault.isEmpty()) {
            var films = vault.get(0).getFilms();
            if (!films.isEmpty()) {
                var film = films.stream().filter(f -> String.valueOf(f.getTmdbId()).equals(tmid)).collect(Collectors.toList());
                if (!film.isEmpty()) {
                    film.get(0).changeStatus();
                    view.successfullyActionView();
                    UserRepository.getInstance().add(u);
                } else {
                    view.filmNotFoundView();
                }
            } else {
                view.filmNotFoundView();
            }
        } else {
            view.vaultNotFoundView();
        }
    }

    /**
     * Adds a book to the specified book vault belonging to the currently logged-in user.
     *
     * @param isbn      The ISBN number of the book to be added.
     * @param vaultName The name of the book vault to which the book should be added.
     */
    private void actionAddBook(String isbn, String vaultName) {
        var vaultList = UserSession.getInstance().getLoggedUser().getBookVaults().stream().filter(bv -> bv.getName().equals(vaultName)).collect(Collectors.toList());
        // test if vaultList exists
        if (vaultList.size() == 0) {
            view.vaultNotFoundView();
            return;
        }
        if (vaultList.get(0).findByIsbn(isbn) != null) {
            view.bookAlreadyExistsView();
            return;
        }
        // test if isbn exists in db
        Book bdb = BookDbRepository.getInstance().findByIsbn(isbn);
        if (bdb == null) {
            // test if isbn exists in the api
            Book b = BookApiRepository.getInstance().getByIsbn(isbn);
            if (b == null) {
                view.bookNotFoundView();
                return;
            }
            vaultList.get(0).addElement(b);
        } else {
            vaultList.get(0).addElement(bdb);
        }
        view.successfullyActionView();
        //update user status
        UserRepository.getInstance().add(UserSession.getInstance().getLoggedUser());
    }

    private void actionSearchBookTitle(String title) {
        view.listOfSearchedBooksView(BookApiRepository.getInstance().getAsListByTitle(title));
    }

    private void actionSearchFilmTitle(String title) {
        view.listOfSearchedFilmsView(FilmApiRepository.getInstance().getAsListByTitle(title));
    }

    /**
     * Deletes a book vault belonging to the currently logged-in user.
     *
     * @param title The title of the book vault to be deleted.
     */
    private void actionDeleteBookVault(String title) {
        User u = UserSession.getInstance().getLoggedUser();
        List<BookVault> bv = u.getBookVaults().stream().filter(v -> v.getName().equals(title)).collect(Collectors.toList());

        if (bv.isEmpty()) {
            view.vaultNotFoundView();
        } else {
            u.removeVault(bv.get(0));
            view.successfullyActionView();
        }
        UserRepository.getInstance().add(u);
    }

    /**
     * Deletes a film vault belonging to the currently logged-in user.
     *
     * @param title The title of the film vault to be deleted.
     */
    private void actionDeleteFilmVault(String title) {
        User u = UserSession.getInstance().getLoggedUser();
        List<FilmVault> fv = u.getFilmVaults().stream().filter(v -> v.getName().equals(title)).collect(Collectors.toList());

        if (fv.isEmpty()) {
            view.vaultNotFoundView();
        } else {
            u.removeVault(fv.get(0));
            view.successfullyActionView();
        }
        UserRepository.getInstance().add(u);
    }

    /**
     * Opens a book vault belonging to the currently logged-in user and shows the list of books in the vault.
     *
     * @param vaultName The name of the book vault to be opened.
     */
    private void actionOpenBookVault(String vaultName) {
        List<BookVault> bv = UserSession.getInstance().getLoggedUser().getBookVaults().stream().filter(v -> v.getName().equals(vaultName)).collect(Collectors.toList());
        if (bv.size() < 1) {
            view.vaultNotFoundView();
            return;
        }
        view.listOfBooksView(bv.get(0).getBooks());
    }

    /**
     * Opens a film vault belonging to the currently logged-in user and shows the list of films in the vault.
     *
     * @param vaultName The name of the film vault to be opened.
     */
    private void actionOpenFilmVault(String vaultName) {
        List<FilmVault> fv = UserSession.getInstance().getLoggedUser().getFilmVaults().stream().filter(v -> v.getName().equals(vaultName)).collect(Collectors.toList());
        if (fv.size() < 1) {
            view.vaultNotFoundView();
            return;
        }
        view.listOfFilmsView(fv.get(0).getFilms());
    }

    /**
     * Shows the status of the currently logged-in user, including their name and the number of books and films in their vaults.
     */
    private void actionStatusUser() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd hh:mm:ss", Locale.US);
        User u = UserSession.getInstance().getLoggedUser();
        String lastConnection = "Never";
        if (u.getLastConnection() != null) {
            lastConnection = formatter.format(u.getLastConnection());
        }
        view.statusView(u.getName(), lastConnection, u.getBookVaults(), u.getFilmVaults());
    }

    /**
     * Performs the logout of the currently logged-in user from the application.
     */
    private void actionLogout() {
        User u = UserSession.getInstance().getLoggedUser();
        if (UserSession.getInstance().logout()) {
            view.resetPrompt();
            view.logoutView(u.getName());
        }
    }

    /**
     * Performs the login of a user in the application.
     *
     * @param name     User's name.
     * @param password User's password.
     */
    private void actionLogin(String name, String password) {
        if (!UserSession.getInstance().login(new User(name, password))) {
            view.loginErrorView();
        } else {
            view.modifyPrompt(name);
            view.welcomeView(name);
        }
    }

    private void actionExit() {
        UserSession.getInstance().logout();
        this.exit = true;
    }
}
