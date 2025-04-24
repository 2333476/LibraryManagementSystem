package com.example.apigatewayservice.presentationlayer.bookdto;

public class BookResponseModel {
    private final String bookId;
    private final String title;
    private final String author;
    private final String isbn;
    private final Double price;
    private final String publishedDate;

    private BookResponseModel(String bookId, String title, String author, String isbn, Double price, String publishedDate) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.publishedDate = publishedDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String bookId;
        private String title;
        private String author;
        private String isbn;
        private Double price;
        private String publishedDate;

        public Builder bookId(String bookId) { this.bookId = bookId; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder author(String author) { this.author = author; return this; }
        public Builder isbn(String isbn) { this.isbn = isbn; return this; }
        public Builder price(Double price) { this.price = price; return this; }
        public Builder publishedDate(String publishedDate) { this.publishedDate = publishedDate; return this; }

        public BookResponseModel build() {
            return new BookResponseModel(bookId, title, author, isbn, price, publishedDate);
        }
    }

    // Getters
    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public Double getPrice() { return price; }
    public String getPublishedDate() { return publishedDate; }
}
