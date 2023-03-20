package com.vaultapp.model.entities;

import jakarta.persistence.*;
import java.net.URL;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book extends VaultItem {
    /**
     * The unique ID of the book.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The title of the book.
     */
    private String title;

    /**
     * The author of the book.
     */
    private String author;
    /**
     * The author of the book.
     */
    private String publishYear;
    /**
     * The ISBN of the book.
     */
    @Column(unique = true)
    private String isbn;
    /**
     * The cover image of the book.
     */
    private URL cover;
    /**
     * The availability status of the book.
     */
    private boolean status;

    /**
     * Creates a new instance of a book.
     */
    public Book() {
    }

    /**
     * Creates a new instance of a book with the specified ISBN.
     *
     * @param isbn the ISBN of the book
     */
    public Book(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Creates a new instance of a book with the specified title, author, publish year, and ISBN.
     *
     * @param title       the title of the book
     * @param author      the author of the book
     * @param publishYear the year the book was published
     * @param isbn        the ISBN of the book
     */
    public Book(String title, String author, String publishYear, String isbn) {
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
        this.isbn = isbn;
    }

    /**
     * Gets the availability status of the book.
     *
     * @return the availability status of the book
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * Sets the availability status of the book.
     *
     * @param status the new availability status of the book
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public URL getCover() {
        return cover;
    }

    public void setCover(URL cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void changeStatus() {
        status = !status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishYear='" + publishYear + '\'' +
                ", isbn='" + isbn + '\'' +
                ", cover='" + cover + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
