package com.vaultapp.model.pojo.book.dao;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaultapp.model.entities.Book;
import com.vaultapp.model.pojo.book.DocsItem;
import com.vaultapp.model.pojo.book.Response;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookApiDao {

    private String booksByTitleURL = "http://openlibrary.org/search.json?title=%s";
    private String coverSrc = "https://covers.openlibrary.org/b/isbn/%s-L.jpg";
    private String bookByIsbnURL = "http://openlibrary.org/search.json?q=%s";

    private static BookApiDao instance;

    static {
        instance = new BookApiDao();
    }

    public static BookApiDao getInstance() {
        return instance;
    }

    public List<Book> readByTitle(String title) {
        List<Book> books = new ArrayList<>();
        Book book;
        URL cover;
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String formatTitle = String.join("+", title.toLowerCase().split(" "));
        String url = String.format(booksByTitleURL, formatTitle);

        try {
            Response r = om.readValue(new URL(url), Response.class);
            List<DocsItem> docs = r.getDocs();
            for (DocsItem doc : docs) {
                String mainTitle = title;
                if (doc.getTitle() != null) {
                    mainTitle = doc.getTitle();
                }

                String author = "";
                if (doc.getAuthorName() != null && doc.getAuthorName().get(0) != null) {
                    author= doc.getAuthorName().get(0);
                }

                String publishDate ="";
                if (doc.getPublishDate() != null && doc.getPublishDate().get(0) != null) {
                    publishDate= doc.getPublishDate().get(0);
                }

                String isbn = "";
                cover = null;
                if (doc.getIsbn() != null && doc.getIsbn().get(0)!= null) {
                    isbn = doc.getIsbn().get(0);
                    cover = new URL(String.format(coverSrc, isbn));
                }

                book = new Book(
                        mainTitle,
                        author,
                        publishDate,
                        isbn
                );

                book.setCover(cover);
                books.add(book);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    public List<Book> readByIsbn(String isbn) {
        List<Book> books = new ArrayList<>();
        Book book;
        URL cover;
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String formatTitle = String.join("+", isbn.toLowerCase().split(" "));
        String url = String.format(bookByIsbnURL, formatTitle);

        try {
            Response r = om.readValue(new URL(url), Response.class);
            List<DocsItem> docs = r.getDocs();
            for (DocsItem doc : docs) {
                String mainTitle = "";
                if (doc.getTitle() != null) {
                    mainTitle = doc.getTitle();
                }

                String author = "";
                if (doc.getAuthorName() != null && doc.getAuthorName().get(0) != null) {
                    author= doc.getAuthorName().get(0);
                }

                String publishDate ="";
                if (doc.getPublishDate() != null && doc.getPublishDate().get(0) != null) {
                    publishDate= doc.getPublishDate().get(0);
                }

                cover = null;
                if (doc.getIsbn() != null && doc.getIsbn().get(0)!= null) {
                    isbn = doc.getIsbn().get(0);
                    cover = new URL(String.format(coverSrc, isbn));
                }

                book = new Book(
                        mainTitle,
                        author,
                        publishDate,
                        isbn
                );

                book.setCover(cover);
                books.add(book);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return books;
    }


}
