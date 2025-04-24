package com.example.bookservice.presentationlayer;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


import jakarta.validation.constraints.NotBlank;

@Data
public class BookRequestModel {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @NotBlank(message = "ISBN is required")
    private String isbn;

    @NotNull(message = "Price is required")
    private double price;

    private String publishedDate;
}
