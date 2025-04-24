package com.example.bookservice.presentationlayer;

import lombok.Data;

@Data
public class BookResponseModel {
    private String bookId;  // public identifier
    private String title;
    private String author;
    private String isbn;
    private Double price;
    private String publishedDate;
}
