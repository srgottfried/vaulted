package com.vaultapp.model.pojo.film.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaultapp.model.entities.Film;
import com.vaultapp.model.pojo.film.*;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FilmApiDao {

    private final String SEARCH_URL = "https://api.themoviedb.org/3/search/movie?api_key=%s&query=%s&page=%d";
    private final String MOVIE_URL = "https://api.themoviedb.org/3/movie/%d?api_key=%s";
    private final String POSTER_URL = "https://image.tmdb.org/t/p/original%s";
    private final String API_KEY = "19ccdf01a305d5f5c3485958c90ef5d6";

    private static FilmApiDao instance;

    static {
        instance = new FilmApiDao();
    }

    private FilmApiDao() {

        ObjectMapper om = new ObjectMapper();

    }

    public static FilmApiDao getInstance() {
        return instance;
    }


    public List<Film> searchByTitle(String title, int page) {
        ObjectMapper objectMapper = new ObjectMapper();
        String formattedTitle = String.join("+", title.split(" "));
        String url = String.format(SEARCH_URL, API_KEY, formattedTitle, page);
        List<Film> films = new ArrayList<>();

        try {
            Response resultPage = objectMapper.readValue(new URL(url), Response.class);
            List<ResultsItem> pageFilms = resultPage.getResults();
            for (ResultsItem a : pageFilms) {
                films.add(parseApiFilm(a));
            }
        } catch (ConnectException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return films;
    }

    private Film parseApiFilm(ResultsItem apiFilm) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.US);
        FilmDetail filmDetail = searchByTmdbId(apiFilm.getId());

        int tmdbId = filmDetail.getId();
        String title = filmDetail.getTitle();
        String posterPath = String.format(POSTER_URL, apiFilm.getPosterPath());
        List<String> genres = new ArrayList<>();
        String overview = filmDetail.getOverview();
        String originalTitle = filmDetail.getOriginalTitle();
        List<String> productionCompanies = new ArrayList<>();
        LocalDate releaseDate;
        if (filmDetail.getReleaseDate().length() != 0) {
            releaseDate = LocalDate.parse(filmDetail.getReleaseDate(), formatter);
        } else {
            releaseDate = null;
        }
        String tagline = filmDetail.getTagline();

        filmDetail.getGenres().forEach(g -> genres.add(g.getName()));
        filmDetail.getProductionCompanies().forEach(p -> productionCompanies.add(p.getName()));

        return new Film(tmdbId,
                title,
                posterPath,
                genres,
                overview,
                originalTitle,
                productionCompanies,
                releaseDate,
                tagline);
    }

    private FilmDetail searchByTmdbId(int id) {
        ObjectMapper objectMapper = new ObjectMapper();
        String url = String.format(MOVIE_URL, id, API_KEY);
        FilmDetail film = null;

        try {
            film = objectMapper.readValue(new URL(url), FilmDetail.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return film;
    }

    public Film getFilmByTmdbId(int id) throws IOException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.US);
        ObjectMapper om = new ObjectMapper();
        String url = String.format(MOVIE_URL, id, API_KEY);
        FilmDetail filmDetail = om.readValue(new URL(url), FilmDetail.class);

        int tmdbId = filmDetail.getId();
        String title = filmDetail.getTitle();
        String posterPath = String.format(POSTER_URL, filmDetail.getPosterPath());
        List<String> genres = new ArrayList<>();
        String overview = filmDetail.getOverview();
        String originalTitle = filmDetail.getOriginalTitle();
        List<String> productionCompanies = new ArrayList<>();
        LocalDate releaseDate;
        if (filmDetail.getReleaseDate().length() != 0) {
            releaseDate = LocalDate.parse(filmDetail.getReleaseDate(), formatter);
        } else {
            releaseDate = null;
        }
        String tagline = filmDetail.getTagline();

        filmDetail.getGenres().forEach(g -> genres.add(g.getName()));
        filmDetail.getProductionCompanies().forEach(p -> productionCompanies.add(p.getName()));

        return new Film(tmdbId,
                title,
                posterPath,
                genres,
                overview,
                originalTitle,
                productionCompanies,
                releaseDate,
                tagline);
    }
}
