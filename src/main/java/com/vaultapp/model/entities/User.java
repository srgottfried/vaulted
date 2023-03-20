package com.vaultapp.model.entities;

import com.vaultapp.utilities.Cipher;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Entity class representing a user in the system.
 * <p>
 * This class is mapped to a database table using the `@Entity` and `@Table` annotations.
 * The table name is specified using the `name` attribute of the `@Table` annotation.
 * <p>
 * The `@Id` annotation is used to mark the field that represents the primary key in the database table.
 * The `@GeneratedValue` annotation is used to specify the primary key generation strategy. In this case,
 * the strategy is `GenerationType.IDENTITY`, which means that the primary key value will be generated
 * by the database when a new row is inserted.
 * <p>
 * The `@Column` annotation is used to specify the mapping between a field and a column in the database table.
 * The `unique` attribute is used to specify that the column has a unique constraint.
 * <p>
 * The `@OneToMany` and `@OneToOne` annotations are used to specify one-to-many and one-to-one relationships, respectively,
 * between `User` and other entity classes. The `cascade` attribute is used to specify which operations
 * should be cascaded to the associated entities. The `mappedBy` attribute is used to specify the field
 * in the associated entity class that is the owner of the relationship.
 * <p>
 * The `@JoinColumn` annotation is used to specify the name of the foreign key column in the database table.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String password;

    @Column(name = "last_connection")
    private LocalDateTime lastConnection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<FilmVault> filmVaults;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<BookVault> bookVaults;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_regalo")
    private Regalo regalo;

    /**
     * Default constructor.
     */
    public User() {
        filmVaults = new ArrayList<>();
        bookVaults = new ArrayList<>();
    }

    /**
     * Constructor with name and password parameters.
     *
     * @param name     the name of the user
     * @param password the password of the user
     */
    public User(String name, String password) {
        this();
        this.name = name;
        this.password = Cipher.getInstance().encrypt(password);
    }

    /**
     * Gets the last connection time of the user.
     *
     * @return the last connection time
     */
    public LocalDateTime getLastConnection() {
        return lastConnection;
    }

    /**
     * Sets the last connection time of the user.
     *
     * @param lastConnection the last connection time
     */
    public void setLastConnection(LocalDateTime lastConnection) {
        this.lastConnection = lastConnection;
    }

    /**
     * Gets the name of the user.
     *
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name the name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the ID of the user.
     *
     * @return the ID of the user
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the user.
     *
     * @param id the ID of the user
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the list of film vaults owned by the user.
     *
     * @return the list of film vaults
     */
    public List<FilmVault> getFilmVaults() {
        return filmVaults;
    }

    /**
     * Sets the list of film vaults owned by the user.
     *
     * @param filmVaults the list of film vaults
     */
    public void setFilmVaults(List<FilmVault> filmVaults) {
        this.filmVaults = filmVaults;
    }

    /**
     * Gets the list of book vaults owned by the user.
     *
     * @return the list of book vaults
     */
    public List<BookVault> getBookVaults() {
        return bookVaults;
    }

    /**
     * Sets the list of book vaults owned by the user.
     *
     * @param bookVaults the list of book vaults
     */
    public void setBookVaults(List<BookVault> bookVaults) {
        this.bookVaults = bookVaults;
    }

    /**
     * Gets the gift owned by the user.
     *
     * @return the gift
     */
    public Regalo getRegalo() {
        return regalo;
    }

    /**
     * Sets the gift owned by the user.
     *
     * @param regalo the gift
     */
    public void setRegalo(Regalo regalo) {
        this.regalo = regalo;
    }

    /**
     * Adds a vault for the user. Symmetrically defines an owner for the vault.
     * This method ensures the bidirectional class relation between `User` and
     * `Vault`.
     *
     * @param vault the vault to be added
     */
    public void addVault(Vault vault) {
        if (vault instanceof FilmVault) {
            filmVaults.add((FilmVault) vault);
            ((FilmVault) vault).setOwner(this);
        } else if (vault instanceof BookVault) {
            bookVaults.add((BookVault) vault);
            ((BookVault) vault).setOwner(this);
        }
    }

    /**
     * Adds a vault for the user. Symmetrically defines an owner for the vault.
     * This method ensures the bidirectional class relation between `User` and
     * `Vault`.
     *
     * @param vault the vault to be added
     */
    public void removeVault(Vault vault) {
        if (vault instanceof BookVault) {
            bookVaults.remove((BookVault) vault);
        } else if (vault instanceof FilmVault) {
            filmVaults.remove((FilmVault) vault);
        }
    }

    /**
     * Finds vaults owned by the user with a specified title.
     *
     * @param title the title of the vaults to be found
     * @return a list of vaults with the specified title
     */
    public List<Vault> findVaultByName(String title) {
        List<Vault> vaults = new LinkedList<>();
        List<BookVault> bv = bookVaults.stream().filter(v -> v.getName().equals(title)).collect(Collectors.toList());
        List<FilmVault> fv = filmVaults.stream().filter(v -> v.getName().equals(title)).collect(Collectors.toList());
        vaults.addAll(bv);
        vaults.addAll(fv);
        return vaults;
    }

    /**
     * Adds a gift for the user. Symmetrically defines an owner for the gift.
     * This method ensures the bidirectional class relation between `User` and
     * `Regalo`.
     *
     * @param regalo the gift to be added
     */
    public void addRegalo(Regalo regalo) {
        this.regalo = regalo;
        regalo.setUser(this);
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
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(Cipher.getInstance().decrypt(password),
                Cipher.getInstance().decrypt(user.password));
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, Cipher.getInstance().decrypt(password));
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
