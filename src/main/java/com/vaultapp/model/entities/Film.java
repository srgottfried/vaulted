package com.vaultapp.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * A class representing a film.
 */
@Entity
@Table(name = "films")
public class Film extends VaultItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int tmdbId;
    private String title;
    private String posterPath;
    @ElementCollection
    private List<String> genres;
    @Column(length = 65535)
    private String overview;
    private String originalTitle;
    @ElementCollection
    private List<String> productionCompanies;
    private LocalDate releaseDate;
    private String tagline;
    private boolean status;

    /**
     * Constructs a new film with the specified properties.
     *
     * @param tmdbId              the TMDB ID of the film
     * @param title               the title of the film
     * @param posterPath          the poster path of the film
     * @param genres              the genres of the film
     * @param overview            the overview of the film
     * @param originalTitle       the original title of the film
     * @param productionCompanies the production companies of the film
     *                            * @param releaseDate       the release date of the film
     *                            * @param tagline           the tagline of the film
     */
    public Film(int tmdbId, String title, String posterPath, List<String> genres, String overview, String originalTitle, List<String> productionCompanies, LocalDate releaseDate, String tagline) {
        this();
        this.tmdbId = tmdbId;
        this.title = title;
        this.posterPath = posterPath;
        this.genres = genres;
        this.overview = overview;
        this.originalTitle = originalTitle;
        this.productionCompanies = productionCompanies;
        this.releaseDate = releaseDate;
        this.tagline = tagline;
    }

    /**
     * Constructs a new film with no information.
     */
    public Film() {
        status = false;

    }

    /**
     * Gets the status of the film.
     *
     * @return `true` if the film is watched, `false` otherwise
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * Sets the status of the film.
     *
     * @param status the status of the film
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Gets the ID of the film.
     *
     * @return the ID of the film
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the film.
     *
     * @param id the ID of the film
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the TMDB ID of the film.
     *
     * @return the TMDB ID of the film
     */
    public int getTmdbId() {
        return tmdbId;
    }

    /**
     * Gets the title of the film.
     *
     * @return the title of the film
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the poster path of the film.
     *
     * @return the poster path of the film
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * Gets the genres of the film.
     *
     * @return the genres of the film
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     * Gets the overview of the film.
     *
     * @return the overview of the film
     */
    public String getOverview() {
        return overview;
    }

    /**
     * Gets the original title of the film.
     *
     * @return the original title of the film
     */
    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     * Gets the production companies of the film.
     *
     * @return the production companies of the film
     */
    public List<String> getProductionCompanies() {
        return productionCompanies;
    }

    /**
     * Gets the release date of the film.
     *
     * @return the release date of the film
     */
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    /**
     * Gets the tagline of the film.
     *
     * @return the tagline of the film
     */
    public String getTagline() {
        return tagline;
    }

    /**
     * Toggles the status of the film.
     */
    public void changeStatus() {
        status = !status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Film)) return false;
        Film film = (Film) o;
        return Objects.equals(id, film.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", genres=" + genres +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
