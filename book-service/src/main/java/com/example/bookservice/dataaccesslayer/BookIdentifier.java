package com.example.bookservice.dataaccesslayer;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class BookIdentifier {

    @Column(name = "book_id") // 👈 Forcer le nom exact
    private String bookId;

    public BookIdentifier() {
        this.bookId = UUID.randomUUID().toString();
    }

    public String getBookId() {
        return bookId;
    }
}
