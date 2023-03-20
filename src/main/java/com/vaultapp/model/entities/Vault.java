package com.vaultapp.model.entities;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * A generic class representing a collection of elements of type T.
 *
 * @param <T> the type of elements in the vault
 */
@MappedSuperclass
public abstract class Vault<T> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String name;

    /**
     * Constructs a new vault with no name.
     */
    public Vault() {
    }

    /**
     * Constructs a new vault with a specified name.
     *
     * @param name the name of the vault
     */
    public Vault(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the vault.
     *
     * @return the name of the vault
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the vault.
     *
     * @param name the name of the vault
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Adds an element to the vault.
     *
     * @param t the element to be added
     */
    public abstract void addElement(T t);

    /**
     * Removes an element from the vault.
     *
     * @param t the element to be removed
     */
    public abstract void deleteElement(T t);

    /**
     * Gets the ID of the vault.
     *
     * @return the ID of the vault
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the vault.
     *
     * @param id the ID of the vault
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare
     * @return `true` if this object is the same as the `o` argument; `false` otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vault)) return false;
        Vault<?> vault = (Vault<?>) o;
        return Objects.equals(name, vault.name);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
