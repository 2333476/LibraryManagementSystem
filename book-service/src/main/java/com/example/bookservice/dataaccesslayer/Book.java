package com.example.bookservice.dataaccesslayer;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "books")
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Identifiant privé

    @Embedded
    @AttributeOverride(
            name = "bookId",
            column = @Column(name = "book_id", nullable = false, unique = true)
    )
    private BookIdentifier bookIdentifier; // Identifiant public

    private String title;
    private String author;
    private String isbn;
    private Double price;
    private String publishedDate;

    public Book() {
        this.bookIdentifier = new BookIdentifier();
    }

    public Book(BookIdentifier bookIdentifier,
                String title, String author, String isbn,
                Double price, String publishedDate) {
        this.bookIdentifier = bookIdentifier;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.publishedDate = publishedDate;
    }
}
