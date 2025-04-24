package com.example.bookservice.businesslogiclayer;

import com.example.bookservice.presentationlayer.BookRequestModel;
import com.example.bookservice.presentationlayer.BookResponseModel;

import java.util.List;

public interface BookService {
    List<BookResponseModel> getAllBooks();
    BookResponseModel getBookByBookId(String bookId);
    BookResponseModel addBook(BookRequestModel bookRequestModel);
    BookResponseModel updateBook(String bookId, BookRequestModel bookRequestModel);
    void deleteBook(String bookId);
}
