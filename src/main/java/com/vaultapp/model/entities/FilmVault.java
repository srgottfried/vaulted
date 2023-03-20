package com.vaultapp.model.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Represents a collection of films.
 */
@Entity
@Table(name = "filmvaults")
public class FilmVault extends Vault<Film> {
    /**
     * The list of films in the vault.
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "films_vaults",
            joinColumns = @JoinColumn(name = "filmvault_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"filmvault_id", "film_id"})
    )
    private List<Film> films;

    /**
     * The owner of the vault.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
    /**
     * Creates a new instance of {@code FilmVault}.
     */
    public FilmVault() {
        films = new ArrayList<>();
    }
    /**
     * Creates a new instance of {@code FilmVault} with the specified name.
     *
     * @param name the name of the film vault
     */
    public FilmVault(String name) {
       super(name);
       films = new ArrayList<>();
    }
    /**
     * Gets the list of films in the vault.
     *
     * @return the list of films in the vault
     */
    public List<Film> getFilms() {
        return films;
    }
    /**
     * Sets the list of films in the vault.
     *
     * @param films the list of films in the vault
     */
    public void setFilms(List<Film> films) {
        this.films = films;
    }
    /**
     * Gets the owner of the vault.
     *
     * @return the owner of the vault
     */
    public User getOwner() {
        return owner;
    }
    /**
     * Sets the owner of the vault.
     *
     * @param owner the owner of the vault
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public void addElement(Film film) {
        if (!films.contains(film)) {
            films.add(film);
        }
    }

    @Override
    public void deleteElement(Film film) {
        films.remove(film);
    }
    /**
     * Finds a film in the vault by its TMDb ID.
     *
     * @param tmid the TMDb ID of the film to find
     * @return the film with the specified TMDb ID, or {@code null} if no such film was found
     */
    public Film findByTmid(String tmid) {
        List<Film> lf = films.stream().filter(f -> String.valueOf(f.getTmdbId()).equals(tmid)).collect(Collectors.toList());
        if (lf.size() > 0) {
            return lf.get(0);
        } else {
            return null;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        String str = name + "(" + films.size() + ")";
        return str;
    }
}
