package com.vaultapp.model.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a collection of books, known as a "vault", that is owned by a specific user.
 * This class extends the `Vault` abstract class and overrides its `addElement` and `deleteElement`
 * methods to add and remove books from the `books` list. It also defines additional methods for
 * retrieving and modifying the name, owner, and list of books in the vault.
 *
 * @Entity: indicates that this class is a JPA entity
 * @Table(name = "bookvaults"): specifies the name of the database table that corresponds to this entity
 */
@Entity
@Table(name = "bookvaults")
public class BookVault extends Vault<Book> {
    /**
     * @ManyToMany(cascade = CascadeType.ALL): specifies the relationship between this entity and the `Book` entity
     * @JoinTable(...): specifies the name and structure of the database table that represents the relationship
     * between this entity and the `Book` entity
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "books_vaults",
            joinColumns = @JoinColumn(name = "bookvault_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"bookvault_id", "book_id"})
    )
    private List<Book> books;

    /**
     * @ManyToOne: specifies the relationship between this entity and the `User` entity
     * @JoinColumn(name = "user_id"): specifies the name of the database column that represents the relationship
     * between this entity and the `User` entity
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    /**
     * Constructs a new `BookVault` instance with the specified name and an empty `books` list.
     *
     * @param name the name of the vault
     */
    public BookVault(String name) {
        super(name);
        books = new ArrayList<>();
    }

    /**
     * Constructs a new `BookVault` instance with no name and an empty `books` list.
     */
    public BookVault() {
        super();
        books = new ArrayList<>();
    }

    /**
     * Returns the name of this `BookVault` instance.
     *
     * @return the name of this `BookVault` instance
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of this `BookVault` instance to the specified value.
     *
     * @param name the new name of this `BookVault` instance
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the list of books in this `BookVault` instance.
     *
     * @return the list of books in this `BookVault` instance
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * Sets the list of books in this `BookVault` instance to the specified value.
     *
     * @param books the new list of books in this `BookVault` instance
     */
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    /**
     * Returns the owner of this `BookVault` instance.
     *
     * @return the owner of this `BookVault` instance
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Sets the owner of this `BookVault` instance to the specified value.
     *
     * @param owner the new owner of this `BookVault` instance
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     * Adds the specified book to the list of books in this `BookVault` instance.
     * If the book is already in the list, it is not added again.
     *
     * @param book the book to add to this `BookVault` instance
     */
    @Override
    public void addElement(Book book) {
        if (!books.contains(book)) {
            books.add(book);
        }
    }

    /**
     * Removes the specified book from the list of books in this `BookVault` instance.
     * If the book is not in the list, this method has no effect.
     *
     * @param book the book to remove from this `BookVault` instance
     */
    @Override
    public void deleteElement(Book book) {
        books.remove(book);
    }

    /**
     * Returns the book in this `BookVault` instance with the specified ISBN, or `null`
     * if no such book exists.
     *
     * @param isbn the ISBN of the book to search for
     * @return the book with the specified ISBN, or `null` if no
     */
    public Book findByIsbn(String isbn) {
        List<Book> lb = books.stream().filter(b -> b.getIsbn().equals(isbn)).collect(Collectors.toList());
        if (lb.size() > 0) {
            return lb.get(0);
        } else {
            return null;
        }
    }

    /**
     * Returns a string representation of this `BookVault` instance.
     * The string consists of the vault name and the number of books it contains,
     * in the format "vaultName(numberOfBooks)".
     *
     * @return a string representation of this `BookVault` instance
     */
    @Override
    public String toString() {
        String str = name + "(" + books.size() + ")";
        return str;
    }
}
