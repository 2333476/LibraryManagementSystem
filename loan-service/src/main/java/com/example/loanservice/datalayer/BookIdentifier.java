package com.example.loanservice.datalayer;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
public class BookIdentifier {

    private String bookId;

    public BookIdentifier() {
        this.bookId = UUID.randomUUID().toString();
    }

    public BookIdentifier(String bookId) {
        this.bookId = bookId;
    }
}
